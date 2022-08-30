package comeon.group.gameloveservice.model;

import comeon.group.gameloveservice.validation.Save;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
* Created by Behrooz.Mohamadi on 14/08/2022.
 */
@Data
public class PlayerModel extends BaseModel<Long> {

    @NotNull(groups = Save.class)
    @NotBlank(groups = Save.class)
    private String firstName;

    @NotNull(groups = Save.class)
    @NotBlank(groups = Save.class)
    private String lastName;

    @Email
    @NotBlank(groups = Save.class)
    private String email;

    private Date birthDate;
}
