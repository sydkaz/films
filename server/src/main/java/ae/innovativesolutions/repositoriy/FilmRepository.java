package ae.innovativesolutions.repositoriy;


import ae.innovativesolutions.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {
    Optional<Film> findBySlugIgnoreCase(String slug);

}