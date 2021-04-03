package ae.innovativesolutions;

public class Film {
    private final Long id;
    String name;
    String description;
    String releaseDate;
    String rating;
    String ticketPrice;
    String country;
    String genre;
    String photo;

    public Film(Long id, String name, String description, String releaseDate, String rating, String ticketPrice, String country, String genre, String photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.ticketPrice = ticketPrice;
        this.country = country;
        this.genre = genre;
        this.photo = photo;
    }
}
