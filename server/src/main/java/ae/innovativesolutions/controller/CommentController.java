package ae.innovativesolutions.controller;

import ae.innovativesolutions.model.Comment;
import ae.innovativesolutions.payload.ApiResponse;
import ae.innovativesolutions.payload.CommentPayload;
import ae.innovativesolutions.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/film/{slug}")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/comment/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createComment(@PathVariable("slug") String slug, @Valid @RequestBody CommentPayload commentPayload,Principal principal){
        Comment result = commentService.createComment(slug,commentPayload);
        messagingTemplate.convertAndSendToUser(principal.getName(),
                "/queue/notification",
                new ApiResponse(true, "Comment Created Successfully"));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{slug}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Comment Created Successfully"));

    }

}
