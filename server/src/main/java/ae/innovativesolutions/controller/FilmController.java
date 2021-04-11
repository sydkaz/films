package ae.innovativesolutions.controller;

import ae.innovativesolutions.actions.FilmActions;
import ae.innovativesolutions.components.FilmActionPerformed;
import ae.innovativesolutions.exceptions.ResourceNotFoundException;
import ae.innovativesolutions.mapper.ModelMapper;
import ae.innovativesolutions.model.Film;
import ae.innovativesolutions.payload.ApiResponse;
import ae.innovativesolutions.payload.FilmPayload;
import ae.innovativesolutions.service.CommentService;
import akka.actor.ActorRef;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Controller
public class FilmController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    Timeout timeout;

    @Autowired
    ActorRef getActorRef;

    @Autowired
    CommentService commentService;


    @MessageMapping("/allfilms")
    @SendTo("/topic/films")
    List<FilmPayload> allMovies() throws ExecutionException, InterruptedException {
        CompletionStage<Object> films = PatternsCS.ask(getActorRef, new FilmActionPerformed(FilmActions.GETALL), timeout);

        List<Film> localFilms = (List<Film>) films.toCompletableFuture().get();

        List<FilmPayload> filmResponses = localFilms.stream().map(f ->
                {
                    return ModelMapper.mapFilmsResponse(f, f.getComments());
                }
        ).collect(Collectors.toList());
        return filmResponses;
    }

    @SneakyThrows
    @MessageMapping(value = "/film/{slug}")
    @ResponseBody
    public ApiResponse getSingleMovie(@DestinationVariable String slug, Principal principal) {
        CompletionStage<Optional<Film>> film = PatternsCS.ask(getActorRef, new FilmActionPerformed(FilmActions.GETONE,slug), timeout)
                .thenApply(obj -> (Optional<Film>) obj);
        Film f = film.toCompletableFuture().get().orElseThrow(
                () -> new ResourceNotFoundException(" Film "," slug",slug));
        FilmPayload filmPayload = ModelMapper.mapFilmResponse(f,f.getComments());
        messagingTemplate.convertAndSendToUser(principal.getName(),
                "/queue/film",
                filmPayload
        );
        // Return an http 200 status code
       return new ApiResponse(true, "Film fetched successfully");
    }


    @MessageMapping(value = "/film/new/create")
    @ResponseBody
    public void createMovie(@RequestBody Film film,Principal principal) throws ExecutionException, InterruptedException {
        CompletionStage<FilmActionPerformed> filmAdded = PatternsCS.ask(getActorRef, new FilmActionPerformed(FilmActions.ADD,film), timeout)
                .thenApply(obj -> (FilmActionPerformed) obj);

        messagingTemplate.convertAndSend(
                "/topic/films",
                allMovies());


        messagingTemplate.convertAndSendToUser(principal.getName(),
                "/queue/notification",
                new ApiResponse(true, "Film Created Successfully"));
    }

}
