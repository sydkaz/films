package ae.innovativesolutions.controller;


import ae.innovativesolutions.components.ActionPerformed;
import ae.innovativesolutions.Actions;
import ae.innovativesolutions.components.FilmNotFoundException;
import ae.innovativesolutions.extension.SpringExtension;
import ae.innovativesolutions.model.Film;
import ae.innovativesolutions.repositoriy.FilmRepository;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scala.concurrent.duration.FiniteDuration;

import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RestController
public class FilmController {



    @Autowired
    Timeout timeout;

    @Autowired
    ActorRef getActorRef;

    @GetMapping("/film/{slug}")
    @ResponseBody
    Film finSingleMovie(@PathVariable String slug) throws ExecutionException, InterruptedException {
        CompletionStage<Optional<Film>> film = PatternsCS.ask(getActorRef, new ActionPerformed(Actions.GETONE,slug), timeout)
                .thenApply(obj -> (Optional<Film>) obj);

        return film.toCompletableFuture().get().orElseThrow(
                () -> new FilmNotFoundException("userId"));

    }

    @GetMapping("/film")
    List<Film> allMovies() throws ExecutionException, InterruptedException {
        CompletionStage<Object> films = PatternsCS.ask(getActorRef, new ActionPerformed(Actions.GETALL), timeout);
        return (List<Film>) films.toCompletableFuture().get();
    }

    @PostMapping("/film")
    String addMovie(@RequestBody Film film) throws ExecutionException, InterruptedException {

        CompletionStage<ActionPerformed> filmAdded = PatternsCS.ask(getActorRef, new ActionPerformed(Actions.ADD,film), timeout)
                .thenApply(obj -> (ActionPerformed) obj);

        return filmAdded.toCompletableFuture().get().getMessage();
    }


}
