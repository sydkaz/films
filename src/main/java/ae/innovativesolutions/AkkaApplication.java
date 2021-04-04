package ae.innovativesolutions;

import ae.innovativesolutions.components.ActionPerformed;
import ae.innovativesolutions.model.Film;
import ae.innovativesolutions.extension.SpringExtension;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.PatternsCS;
import akka.util.Timeout;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import scala.concurrent.duration.FiniteDuration;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;

@Configuration
@EnableAutoConfiguration
@ComponentScan("ae.innovativesolutions.configuration")
public class AkkaApplication {

    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(AkkaApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8083"));

        ApplicationContext context = app.run(args);


        ActorSystem system = context.getBean(ActorSystem.class);
        SpringExtension ext = context.getBean(SpringExtension.class);

        ActorRef greeter = system.actorOf(
                ext.props("filmActor"));


        FiniteDuration duration = FiniteDuration.create(1, TimeUnit.SECONDS);
        Timeout timeout = Timeout.durationToTimeout(duration);


        CompletionStage<Optional<Film>> film = PatternsCS.ask(greeter, new ActionPerformed(Actions.GETONE,"beetlejuice"), timeout)
                .thenApply(obj -> (Optional<Film>) obj);


        Film f = film.toCompletableFuture().get().orElseGet(() -> {
            return new Film();
        });
        System.out.println(f.getName());

        //Future<Object> result = ask(greeter, new Greet("John"), timeout);
    }
}
