package comeon.group.gameloveservice.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Behrooz.Mohamadi on 14/08/2022.
 */
@Data
@Entity
@Table(name = "tbl_game_lover")
public class GameLoverEntity extends BaseEntity<GameLoverId> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private GameLoverId id;
    private boolean love;

    @Override
    public String getSelectTitle() {
        return null;
    }
}
