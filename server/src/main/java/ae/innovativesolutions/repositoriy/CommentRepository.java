package ae.innovativesolutions.repositoriy;


import ae.innovativesolutions.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //Optional<Comment> findBySlug(String slug);
}