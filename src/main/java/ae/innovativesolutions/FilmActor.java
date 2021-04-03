package ae.innovativesolutions;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.FI;

class FilmActor extends AbstractActor {
    private FilmService filmService = new FilmService();

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
            if(action.getAction()==Actions.GETALL)
                sender().tell(filmService.getFilms(), getSelf());
            else if(action.getAction()==Actions.GETONE)
                sender().tell(filmService.getFilm(action.getSlug()), getSelf());
            else if(action.getAction()==Actions.ADD) {
                    filmService.addFilm(action.getFilm());
                    sender().tell(new ActionPerformed(String.format("Film %s Added.",action.getFilm().getName())), getSelf());
                }
            else
                sender().tell(new ActionPerformed("Resource not availabel"), getSelf());



        };
    }
}
