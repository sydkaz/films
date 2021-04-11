package ae.innovativesolutions.service;

import ae.innovativesolutions.exceptions.AppException;
import ae.innovativesolutions.exceptions.ResourceNotFoundException;
import ae.innovativesolutions.model.Comment;
import ae.innovativesolutions.model.Film;
import ae.innovativesolutions.model.User;
import ae.innovativesolutions.payload.CommentPayload;
import ae.innovativesolutions.repositoriy.CommentRepository;
import ae.innovativesolutions.repositoriy.FilmRepository;
import ae.innovativesolutions.repositoriy.UserRepository;
import ae.innovativesolutions.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Service
@Transactional
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    UserRepository userRepository;

    public Comment createComment(String slug, @Valid CommentPayload commentPayload) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Film film = filmRepository.findBySlugIgnoreCase(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Film ", "Slug ",slug));

        User user = userRepository.findById(jwtUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User ", "Id ",jwtUser.getId()));

        Comment c = new Comment(film, user, commentPayload.getComment());
        Comment result =  commentRepository.save(c);

        return result;
    }
}
