package ae.innovativesolutions.components;


import ae.innovativesolutions.actions.FilmActions;
import ae.innovativesolutions.model.Film;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserActionPerformed {
    FilmActions action;
    String slug;
    Film film;
    String message;

    public UserActionPerformed(String message){
        this.message = message;
    }
    public UserActionPerformed(FilmActions action){
        this.action = action;
    }
    public UserActionPerformed(){

    }
    public UserActionPerformed(FilmActions action, String slug){
        this.action = action;
        this.slug = slug;
    }
    public UserActionPerformed(FilmActions action, Film film){
        this.action = action;
        this.film = film;
    }

    public FilmActions getAction() {
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
