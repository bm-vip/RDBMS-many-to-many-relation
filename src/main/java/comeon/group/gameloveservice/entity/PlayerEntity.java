package comeon.group.gameloveservice.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Behrooz.Mohamadi on 14/08/2022.
 */
@Data
@Entity
@Table(name = "tbl_player")
@Where(clause = "deleted=false")
public class PlayerEntity extends BaseEntity<Long> implements LogicalDeleted {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_player", sequenceName = "seq_player", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_player")
    private Long id;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    private boolean deleted;

    @Override
    public String getSelectTitle() {
        return String.format("%s %s",firstName,lastName);
    }
}
