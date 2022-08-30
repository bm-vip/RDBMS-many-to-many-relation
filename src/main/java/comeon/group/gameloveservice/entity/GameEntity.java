package comeon.group.gameloveservice.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by Behrooz.Mohamadi on 14/08/2022.
 */
@Data
@Entity
@Table(name = "tbl_game")
@Where(clause = "deleted=false")
public class GameEntity extends BaseEntity<Long> implements LogicalDeleted {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_game", sequenceName = "seq_game", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_game")
    private Long id;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String title;

    private boolean deleted;

    @Override
    public String getSelectTitle() {
        return title;
    }
}
