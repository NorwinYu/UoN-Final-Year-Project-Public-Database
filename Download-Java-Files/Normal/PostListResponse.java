package api.iti0208.data.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostListResponse {

    private List<PostOverview> posts;
    private int amountOfPages;

}
