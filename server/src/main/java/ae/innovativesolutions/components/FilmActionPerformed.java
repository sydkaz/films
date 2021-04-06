package ae.innovativesolutions.components;


import ae.innovativesolutions.actions.FilmActions;
import ae.innovativesolutions.model.Film;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FilmActionPerformed {
    FilmActions action;
    String slug;
    Film film;
    String message;

    public FilmActionPerformed(String message){
        this.message = message;
    }
    public FilmActionPerformed(FilmActions action){
        this.action = action;
    }
    public FilmActionPerformed(){

    }
    public FilmActionPerformed(FilmActions action, String slug){
        this.action = action;
        this.slug = slug;
    }
    public FilmActionPerformed(FilmActions action, Film film){
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
