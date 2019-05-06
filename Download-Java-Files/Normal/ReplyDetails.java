package api.iti0208.data.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDetails {
    private long id;
    private String reply;
    private String fileLocation;
    private Date postedAt;
    private String postedBy;
    private boolean canDelete;
}
