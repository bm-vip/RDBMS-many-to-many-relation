package comeon.group.gameloveservice.model;

import comeon.group.gameloveservice.validation.Save;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
* Created by Behrooz.Mohamadi on 14/08/2022.
 */
@Data
public class GameModel extends BaseModel<Long> {

    @NotNull(groups = Save.class)
    @NotBlank(groups = Save.class)
    private String title;
}
