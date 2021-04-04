package ae.innovativesolutions.actors;

import ae.innovativesolutions.components.ActionPerformed;

import ae.innovativesolutions.Actions;
import ae.innovativesolutions.repositoriy.FilmRepository;
import ae.innovativesolutions.services.FilmService;
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
    FilmService filmService;

    @Autowired
    FilmRepository filmRepository;
    public static Props props() {
        return Props.create(FilmActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ActionPerformed.class, handleAction())
                .build();
    }

    private FI.UnitApply<ActionPerformed> handleAction() {
        return action -> {
            if(action.getAction()== Actions.GETALL)
                sender().tell(filmRepository.findAll(), getSelf());
            else if(action.getAction()==Actions.GETONE)
                sender().tell(filmRepository.findBySlug(action.getSlug()), getSelf());
            else if(action.getAction()==Actions.ADD) {
                    filmRepository.save(action.getFilm());
                    sender().tell(new ActionPerformed(String.format("Film %s Added.",action.getFilm().getName())), getSelf());
                }
            else
                sender().tell(new ActionPerformed("Resource not available"), getSelf());



        };
    }
}
