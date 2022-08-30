package comeon.group.gameloveservice.model;

import comeon.group.gameloveservice.entity.GameLoverId;
import lombok.Data;

/**
 * Created by Behrooz.Mohamadi on 14/08/2022.
 */
@Data
public class GameLoverModel extends BaseModel<GameLoverId>{

    private Boolean love;
}
