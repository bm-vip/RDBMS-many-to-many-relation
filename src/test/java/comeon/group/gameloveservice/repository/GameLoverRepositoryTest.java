package comeon.group.gameloveservice.repository;

import comeon.group.gameloveservice.entity.GameLoverEntity;
import comeon.group.gameloveservice.entity.GameLoverId;
import org.assertj.core.api.Assertions;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.Commit;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameLoverRepositoryTest {

    @Autowired
    private GameLoverRepository gameLoverRepository;

    @Test
    @Order(1)
    @Commit
    public void save_shouldSaveGameLoverEntityToDatabase() {
        GameLoverEntity gameLoverEntity = new GameLoverEntity();
        gameLoverEntity.setId(new GameLoverId(3L,3L));
        gameLoverEntity.setLove(true);
        gameLoverRepository.save(gameLoverEntity);

        Assertions.assertThat(gameLoverEntity.getId()).isEqualTo(new GameLoverId(3L,3L));
    }

    @Test
    @Order(2)
    @Commit
    public void deleteById_shouldDeleteByIdFromDatabase(){
        gameLoverRepository.deleteById(new GameLoverId(3L,3L));
        Optional<GameLoverEntity> optionalGameLover = gameLoverRepository.findById(new GameLoverId(3L,3L));

        Assertions.assertThat(optionalGameLover).isEmpty();
    }

    @Test
    void save_shouldExceptionThrown_thenAssertionSucceeds() {
        var exception = assertThrows(JpaSystemException.class, () -> gameLoverRepository.saveAndFlush(new GameLoverEntity()));

        Assertions.assertThat(exception.getMessage()).contains("null id generated for");
    }

    @Test
    public void update_shouldUpdateGameLoverEntityToDatabase() {
        GameLoverEntity gameLoverEntity = gameLoverRepository.findById(new GameLoverId(1L,1L)).get();
        gameLoverEntity.setLove(false);
        GameLoverEntity gameLoverUpdated = gameLoverRepository.save(gameLoverEntity);

        Assertions.assertThat(gameLoverUpdated.isLove()).isEqualTo(false);
    }

    @Test
    public void findById_shouldReturnGameLoverEntity() {
        Optional<GameLoverEntity> optional = gameLoverRepository.findById(new GameLoverId(1L,1L));

        Assertions.assertThat(optional).isPresent();
        Assertions.assertThat(optional.get().isLove()).isEqualTo(true);
    }

    @Test
    public void findAll_shouldReturnPageableGameLoverEntities() {
        Page<GameLoverEntity> page = gameLoverRepository.findAll(Pageable.ofSize(10));

        Assertions.assertThat(page).isNotEmpty().size().isEqualTo(3);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(3);
    }

    @Test
    public void findAllByPlayerId_shouldReturnPageableGameLoverEntities() {
        Page<GameLoverEntity> page = gameLoverRepository.findAllByPlayerId(1L,Pageable.ofSize(10));

        Assertions.assertThat(page).isNotEmpty().size().isEqualTo(2);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(2);
        Assertions.assertThat(page.getContent().get(0).getId().getGame().getTitle()).isEqualTo("Game A");
        Assertions.assertThat(page.getContent().get(1).getId().getGame().getTitle()).isEqualTo("Game B");
    }
    @Test
    public void findAllByPlayerIdAndLoved_shouldReturnPageableGameLoverEntities() {
        Page<GameLoverEntity> page = gameLoverRepository.findAllByPlayerIdAndLoved(1L,Pageable.ofSize(10));

        Assertions.assertThat(page).isNotEmpty().size().isEqualTo(2);
        Assertions.assertThat(page.getTotalElements()).isEqualTo(2);
        Assertions.assertThat(page.getContent().get(0).getId().getGame().getTitle()).isEqualTo("Game A");
        Assertions.assertThat(page.getContent().get(1).getId().getGame().getTitle()).isEqualTo("Game B");
    }
}
