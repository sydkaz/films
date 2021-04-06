package ae.innovativesolutions.actors;

import ae.innovativesolutions.components.FilmActionPerformed;

import ae.innovativesolutions.actions.FilmActions;
import ae.innovativesolutions.repositoriy.FilmRepository;
import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.FI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FilmActor extends AbstractActor {

    @Autowired
    FilmRepository filmRepository;
    public static Props props() {
        return Props.create(FilmActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(FilmActionPerformed.class, handleAction())
                .build();
    }

    private FI.UnitApply<FilmActionPerformed> handleAction() {
        return action -> {
            if(action.getAction()== FilmActions.GETALL)
                sender().tell(filmRepository.findAll(), getSelf());
            else if(action.getAction()== FilmActions.GETONE)
                sender().tell(filmRepository.findBySlugIgnoreCase(action.getSlug()), getSelf());
            else if(action.getAction()== FilmActions.ADD) {
                    filmRepository.save(action.getFilm());
                    sender().tell(new FilmActionPerformed(String.format("Film %s Added.",action.getFilm().getName())), getSelf());
                }
            else
                sender().tell(new FilmActionPerformed("Resource not available"), getSelf());



        };
    }
}
