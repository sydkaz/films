package ae.innovativesolutions;

import ae.innovativesolutions.actions.FilmActions;
import ae.innovativesolutions.components.FilmActionPerformed;
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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.CacheControl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import scala.concurrent.duration.FiniteDuration;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"ae.innovativesolutions.repositoriy"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@EnableTransactionManagement
@ComponentScan("ae.innovativesolutions.configuration")
public class AkkaApplication implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Register resource handler for images
        registry.addResourceHandler("/upload/**").addResourceLocations("file://" + System.getProperty("user.dir") + "/src/main/upload/");
    }

    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(AkkaApplication.class);


        ApplicationContext context = app.run(args);


        ActorSystem system = context.getBean(ActorSystem.class);
        SpringExtension ext = context.getBean(SpringExtension.class);

        ActorRef greeter = system.actorOf(
                ext.props("filmActor"));


        FiniteDuration duration = FiniteDuration.create(1, TimeUnit.SECONDS);
        Timeout timeout = Timeout.durationToTimeout(duration);


        CompletionStage<Optional<Film>> film = PatternsCS.ask(greeter, new FilmActionPerformed(FilmActions.GETONE,"beetlejuice"), timeout)
                .thenApply(obj -> (Optional<Film>) obj);


        Film f = film.toCompletableFuture().get().orElseGet(() -> {
            return new Film();
        });
        System.out.println(f.getName());

        //Future<Object> result = ask(greeter, new Greet("John"), timeout);
    }
}
