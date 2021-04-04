package ae.innovativesolutions.components;

public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException(String slug) {
        super("Could not find movie " + slug);
    }
}