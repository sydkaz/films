package ae.innovativesolutions;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.FI;

public class FilmActor extends AbstractActor {
    private FilmService filmService = new FilmService();

    static Props props() {
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
            if(action.getAction().equals(Actions.GETALL))
                sender().tell(filmService.getFilms(), getSelf());

        };
    }
}
