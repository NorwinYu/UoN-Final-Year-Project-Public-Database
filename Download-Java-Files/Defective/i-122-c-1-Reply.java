package api.iti0208.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 5)
    private String reply;

    @NotNull
    private Long postId;

    private String fileLocation;


    public Reply(String reply, Long postId) {
        this.reply = reply;
        this.postId = postId;
    }

}
