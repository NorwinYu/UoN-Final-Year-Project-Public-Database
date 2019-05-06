package api.iti0208.data.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInput {

    @NotNull
    @NotEmpty
    @Size(min = 3)
    private String title;

    @NotNull
    @NotEmpty
    private String topic;

    @NotNull
    @NotEmpty
    @Size(min = 5)
    private String description;

    private String rewardDescription;

    private String fileLocation;
}
