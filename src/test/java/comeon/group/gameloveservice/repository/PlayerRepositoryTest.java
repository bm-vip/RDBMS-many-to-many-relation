package comeon.group.gameloveservice.repository;

import comeon.group.gameloveservice.entity.PlayerEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    @Order(1)
    @Commit
    public void save_shouldSavePlayerEntityToDatabase() {
        PlayerEntity PlayerEntity = new PlayerEntity();
        PlayerEntity.setFirstName("Player D");
        PlayerEntity.setLastName("D");
        PlayerEntity.setEmail("d@d.d");
        PlayerEntity.setBirthDate(new Date());
        playerRepository.save(PlayerEntity);

        Assertions.assertThat(PlayerEntity.getId()).isEqualTo(4);
    }

    @Test
    @Order(2)
    @Commit
    public void deleteById_shouldDeleteByIdFromDatabase() {
        playerRepository.deleteById(4L);
        Optional<PlayerEntity> parentOptional = playerRepository.findById(4L);

        Assertions.assertThat(parentOptional).isEmpty();
    }

    @Test
    void save_shouldExceptionThrown_thenAssertionSucceeds() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> playerRepository.saveAndFlush(new PlayerEntity()));

        Assertions.assertThat(exception.getMessage()).contains("must not be null");
    }

    @Test
    public void update_shouldUpdatePlayerEntityToDatabase() {
        PlayerEntity PlayerEntity = playerRepository.findById(3L).get();
        PlayerEntity.setEmail("test@c.c");
        PlayerEntity playerUpdated = playerRepository.save(PlayerEntity);

        Assertions.assertThat(playerUpdated.getEmail()).isEqualTo("test@c.c");
    }

    @Test
    public void findById_shouldReturnPlayerEntity() {
        Optional<PlayerEntity> optional = playerRepository.findById(1L);

        Assertions.assertThat(optional).isPresent();
        Assertions.assertThat(optional.get().getFirstName()).isEqualTo("Player A");
    }

    @Test
    public void findAll_shouldReturnPageablePlayerEntities() {
        Page<PlayerEntity> page = playerRepository.findAll(PageRequest.ofSize(10));

        Assertions.assertThat(page).isNotEmpty().size().isEqualTo(3);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(3);
    }

    @Test
    public void countAll_shouldReturnTotalNumberOfCompanies() {
        long count = playerRepository.count();

        Assertions.assertThat(count).isEqualTo(3);
    }
}
