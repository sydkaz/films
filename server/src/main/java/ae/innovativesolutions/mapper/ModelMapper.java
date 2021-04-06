package ae.innovativesolutions.mapper;

import ae.innovativesolutions.model.Comment;
import ae.innovativesolutions.model.Film;
import ae.innovativesolutions.model.User;
import ae.innovativesolutions.payload.FilmPayload;

import java.util.List;


public class ModelMapper {
    public static FilmPayload mapFilmsResponse(Film film, List<Comment> comment){
        FilmPayload  filmPayload = new FilmPayload(film.getId(),film.getName(),film.getSlug(),film.getDescription(),film.getReleaseDate(),film.getRating(),
                film.getTicketPrice(),film.getCountry(),film.getGenre(),film.getPhoto());

        comment.forEach(c->{
            System.out.println(c.getUser().getEmail());
            filmPayload.setUserName(c.getUser().getUsername());
            filmPayload.getComments().add(c.getComment());
        });
        return filmPayload;
    }

    public static FilmPayload mapFilmResponse(Film film, List<Comment> comment){
        FilmPayload  filmPayload = new FilmPayload(film.getId(),film.getName(),film.getSlug(),film.getDescription(),film.getReleaseDate(),film.getRating(),
                film.getTicketPrice(),film.getCountry(),film.getGenre(),film.getPhoto());

        comment.forEach(c->{
            System.out.println(c.getUser().getEmail());
            filmPayload.setUserName(c.getUser().getUsername());
            filmPayload.getComments().add(c.getComment());
        });
        return filmPayload;
    }
}
