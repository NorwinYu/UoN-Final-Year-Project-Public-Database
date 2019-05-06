package api.iti0208.data.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetails {
    private long id;
    private String title;
    private String topic;
    private String description;
    private String rewardDescription;
    private String fileLocation;
    private Date postedAt;
    private String postedBy;
    private boolean canDelete;
    private List<ReplyDetails> replies;
}
