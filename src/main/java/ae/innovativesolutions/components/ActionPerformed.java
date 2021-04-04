package ae.innovativesolutions.components;


import ae.innovativesolutions.Actions;
import ae.innovativesolutions.model.Film;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActionPerformed {
    Actions action;
    String slug;
    Film film;
    String message;

    public ActionPerformed(String message){
        this.message = message;
    }
    public ActionPerformed(Actions action){
        this.action = action;
    }
    public ActionPerformed(){

    }
    public ActionPerformed(Actions action,String slug){
        this.action = action;
        this.slug = slug;
    }
    public ActionPerformed(Actions action,Film film){
        this.action = action;
        this.film = film;
    }

    public Actions getAction() {
        return action;
    }

    public String getSlug() {
        return slug;
    }

    public Film getFilm() {
        return film;
    }

    public String getMessage() {
        return message;
    }
}
