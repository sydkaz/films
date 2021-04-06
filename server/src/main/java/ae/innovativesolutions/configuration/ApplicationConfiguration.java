package ae.innovativesolutions.configuration;

import ae.innovativesolutions.extension.SpringExtension;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import scala.concurrent.duration.FiniteDuration;
import java.util.concurrent.TimeUnit;

@Configuration
@Lazy
@ComponentScan(basePackages = { "ae.innovativesolutions.service",
        "ae.innovativesolutions.actors", "ae.innovativesolutions.extension","ae.innovativesolutions.controller"
        ,"ae.innovativesolutions.model"
        ,"ae.innovativesolutions.repositoriy","ae.innovativesolutions.components","ae.innovativesolutions.mockdb","ae.innovativesolutions.security"})
public class ApplicationConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SpringExtension springExtension;

    @Autowired
    ActorSystem system;

    @Autowired
    SpringExtension ext;

    /**
     * Actor system singleton for this application.
     */
    @Bean
    public ActorSystem actorSystem() {

        ActorSystem system = ActorSystem
                .create("AkkaTaskProcessing", akkaConfiguration());

        // Initialize the application context in the Akka Spring Extension
        springExtension.initialize(applicationContext);
        return system;
    }

    /**
     * Read configuration from application.conf file
     */
    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }

    /**
     * Simple H2 based in memory backend using a connection pool.
     * Creates th only table needed.
     */

    @Bean
    public Timeout getTimeOut() {
        FiniteDuration duration = FiniteDuration.create(1, TimeUnit.SECONDS);
        return   Timeout.durationToTimeout(duration);
    }

    @Bean
    public ActorRef getActorRef() {
        return system.actorOf(ext.props("filmActor"));
    }



}
