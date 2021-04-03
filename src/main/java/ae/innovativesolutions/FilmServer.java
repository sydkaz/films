package ae.innovativesolutions;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;

import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;


class FilmServer extends HttpApp {

    private final ActorRef filmActor;

    Timeout timeout = new Timeout(Duration.create(3, TimeUnit.HOURS));

    FilmServer(ActorRef filmActor) {
        this.filmActor = filmActor;
    }

    @Override
    public Route routes() {
        return path("films", this::getFilms);

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

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("filmServer");
        ActorRef filmActorRef = system.actorOf(FilmActor.props(), "filmActor");
        FilmServer server = new FilmServer(filmActorRef);
        server.startServer("localhost", 8282, system);
    }

}
