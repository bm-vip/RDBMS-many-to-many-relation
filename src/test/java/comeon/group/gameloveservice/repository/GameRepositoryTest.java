package comeon.group.gameloveservice.repository;

import comeon.group.gameloveservice.entity.GameEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepositor;

    @Test
    @Order(1)
    @Commit
    public void save_shouldSaveGameEntityToDatabase() {
        GameEntity GameEntity = new GameEntity();
        GameEntity.setTitle("test Game");
        gameRepositor.save(GameEntity);

        Assertions.assertThat(GameEntity.getId()).isEqualTo(4L);
    }

    @Test
    @Order(2)
    @Commit
    public void deleteById_shouldDeleteByIdFromDatabase(){
        gameRepositor.deleteById(4L);
        Optional<GameEntity> optionalGame = gameRepositor.findById(4L);

        Assertions.assertThat(optionalGame).isEmpty();
    }

    @Test
    void save_shouldExceptionThrown_thenAssertionSucceeds() {
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> gameRepositor.saveAndFlush(new GameEntity()));

        Assertions.assertThat(exception.getMessage()).contains("must not be null");
    }
    @Test
    public void update_shouldUpdateGameEntityToDatabase(){
        GameEntity gameEntity = gameRepositor.findById(1L).get();
        gameEntity.setTitle("Game test");
        GameEntity gameUpdated = gameRepositor.save(gameEntity);

        Assertions.assertThat(gameUpdated.getTitle()).isEqualTo("Game test");
    }

    @Test
    public void findById_shouldReturnGameEntity() {
        Optional<GameEntity> optional = gameRepositor.findById(1L);

        Assertions.assertThat(optional).isPresent();
        Assertions.assertThat(optional.get().getTitle()).isEqualTo("Game A");
    }

    @Test
    public void findAll_shouldReturnPageableGameEntities() {
        Page<GameEntity> page = gameRepositor.findAll(Pageable.ofSize(10));

        Assertions.assertThat(page).isNotEmpty().size().isEqualTo(3);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(3);
    }
}
