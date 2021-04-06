package ae.innovativesolutions.controller;


import ae.innovativesolutions.components.FilmActionPerformed;
import ae.innovativesolutions.actions.FilmActions;
import ae.innovativesolutions.exceptions.ResourceNotFoundException;
import ae.innovativesolutions.mapper.ModelMapper;
import ae.innovativesolutions.model.Film;
import ae.innovativesolutions.payload.ApiResponse;
import ae.innovativesolutions.payload.FilmPayload;
import akka.actor.ActorRef;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@RestController
public class FilmController {



    @Autowired
    Timeout timeout;

    @Autowired
    ActorRef getActorRef;

    @GetMapping("/film/{slug}")
    @ResponseBody
    FilmPayload finSingleMovie(@PathVariable String slug) throws ExecutionException, InterruptedException {
        CompletionStage<Optional<Film>> film = PatternsCS.ask(getActorRef, new FilmActionPerformed(FilmActions.GETONE,slug), timeout)
                .thenApply(obj -> (Optional<Film>) obj);

        Film f = film.toCompletableFuture().get().orElseThrow(
                () -> new ResourceNotFoundException(" Film "," slug",slug));

        FilmPayload filmPayload = ModelMapper.mapFilmResponse(f,f.getComments());

        return filmPayload;

    }

    @GetMapping("/film")
    List<FilmPayload> allMovies() throws ExecutionException, InterruptedException {
        CompletionStage<Object> films = PatternsCS.ask(getActorRef, new FilmActionPerformed(FilmActions.GETALL), timeout);

        List<Film> localFilms = (List<Film>) films.toCompletableFuture().get();

        List<FilmPayload> filmResponses = localFilms.stream().map(f->
                {return ModelMapper.mapFilmsResponse(f,f.getComments());}
            ).collect(Collectors.toList());

        return filmResponses;
    }

    @PostMapping("/film/create")
    ResponseEntity<?> addMovie(@RequestBody Film film) throws ExecutionException, InterruptedException {

        CompletionStage<FilmActionPerformed> filmAdded = PatternsCS.ask(getActorRef, new FilmActionPerformed(FilmActions.ADD,film), timeout)
                .thenApply(obj -> (FilmActionPerformed) obj);



        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{filmId}")
                .buildAndExpand(filmAdded.toCompletableFuture().get().getMessage()).toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Poll Created Successfully"));
    }


}
