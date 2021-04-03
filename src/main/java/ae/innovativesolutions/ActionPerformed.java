package ae.innovativesolutions;

enum Actions {
    ADD,
    GETONE,
    GETALL
}

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
