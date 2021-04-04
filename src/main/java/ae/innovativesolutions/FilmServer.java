package ae.innovativesolutions;


import ae.innovativesolutions.components.ActionPerformed;
import ae.innovativesolutions.model.Film;
import akka.actor.ActorRef;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.model.Uri;
import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import scala.concurrent.duration.Duration;

import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.http.javadsl.server.PathMatchers.segment;

class FilmServer extends HttpApp {
    @Autowired
    ActorRef filmActor;

    Timeout timeout = new Timeout(Duration.create(3, TimeUnit.HOURS));



    @Override
    public Route routes() {
        return path("", () -> route( pathEnd(() -> redirect(Uri.create("/films"), StatusCodes.TEMPORARY_REDIRECT)) ))
                .orElse(path(segment("films"),()->route(getFilms())))
                .orElse(path(segment("films").slash(segment()), slug -> route(getFilm(slug))))
                .orElse(path(segment("films"),()->route(addFilm())));

    }

    private Route getFilms() {
        return route(get(() -> {
                    CompletionStage<List> films = PatternsCS.ask(filmActor, new ActionPerformed(Actions.GETALL), timeout)
                            .thenApply(obj -> new ArrayList<>(Arrays.asList(obj)));
                    return onSuccess(() -> films, performed -> {
                        if (null != performed)
                            return complete(StatusCodes.OK, performed, Jackson.marshaller());
                        else
                            return complete(StatusCodes.NOT_FOUND);
                    });
                })
        );
    }

    private Route addFilm() {
        return post(() -> entity(Jackson.unmarshaller(Film.class), film -> {
            CompletionStage<ActionPerformed> filmAdded = PatternsCS.ask(filmActor, new ActionPerformed(Actions.ADD,film), timeout)
                    .thenApply(obj -> (ActionPerformed) obj);

            return onSuccess(() -> filmAdded, performed -> {
                return complete(StatusCodes.CREATED, performed.getMessage(), Jackson.marshaller());
            });
        }));
    }
    private Route getFilm(String slug) {
        return get(() -> {
            CompletionStage<Optional<Film>> film = PatternsCS.ask(filmActor, new ActionPerformed(Actions.GETONE,slug), timeout)
                    .thenApply(obj -> (Optional<Film>) obj);

            return onSuccess(() -> film, performed -> {
                if (performed.isPresent())
                    return complete(StatusCodes.OK, performed.get(), Jackson.marshaller());
                else
                    return complete(StatusCodes.NOT_FOUND);
            });
        });
    }



}
