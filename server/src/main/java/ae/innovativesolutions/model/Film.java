package ae.innovativesolutions.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="film")
public class Film {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    @Column(name="name", columnDefinition = "TEXT")
    String name;

    @Column(name="slug", columnDefinition = "TEXT")
    String slug;

    @Column(name="description", columnDefinition = "TEXT")
    String description;


    @Column(name="releaseDate", columnDefinition = "TEXT")
    String releaseDate;

    @Column(name="rating", columnDefinition = "TEXT")
    String rating;

    @Column(name="ticketPrice", columnDefinition = "TEXT")
    String ticketPrice;

    @Column(name="country", columnDefinition = "TEXT")
    String country;

    @Column(name="genre", columnDefinition = "TEXT")
    String genre;
    @Lob
    @Column(name="photo")
    String photo;
    public Film(){
        this.slug = "";
    }

    @OneToMany(
            mappedBy = "film",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<Comment> comments = new ArrayList<>();

    public Film(int id,String slug, String name, String description, String releaseDate, String rating, String ticketPrice, String country, String genre, String photo) {
        this.id = id;
        this.slug = slug;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.ticketPrice = ticketPrice;
        this.country = country;
        this.genre = genre;
        this.photo = photo;
    }

    public List<Comment> getComments() {
        return comments;
    }


    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rating='" + rating + '\'' +
                ", ticketPrice='" + ticketPrice + '\'' +
                ", country='" + country + '\'' +
                ", genre='" + genre + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
