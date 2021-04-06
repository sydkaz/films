package ae.innovativesolutions.payload;


import ae.innovativesolutions.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Syed.
 */
public class CommentPayload {

    @NotBlank
    @Size(min = 6, max = 200)
    private String comment;

    public CommentPayload() {

    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
