package api.iti0208.data.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPatchInput {

    @NotNull
    @Size(min = 3)
    private String title;

    @NotNull
    @Size(min = 5)
    private String description;
}
