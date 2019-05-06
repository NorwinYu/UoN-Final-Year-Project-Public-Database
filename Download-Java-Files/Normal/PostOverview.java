package api.iti0208.data.output;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostOverview {
    private long id;
    private String title;
    private String topic;
    private String description;
    private Date postedAt;
    private String postedBy;
}
