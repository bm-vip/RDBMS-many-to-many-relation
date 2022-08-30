package comeon.group.gameloveservice.service;

import comeon.group.gameloveservice.entity.GameLoverEntity;
import comeon.group.gameloveservice.entity.GameLoverId;
import comeon.group.gameloveservice.model.GameLoverModel;
import comeon.group.gameloveservice.model.GameModel;
import comeon.group.gameloveservice.model.LovedGameModel;
import comeon.group.gameloveservice.repository.GameLoverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GameLoverServiceTest {

    @MockBean
    GameLoverRepository gameLoverRepository;
    @SpyBean
    GameLoverService gameLoverService;

    @Test
    public void toggleLove_shoudChangeLoveStateInGameLoverEntity(){
        //mock db
        GameLoverEntity gameLoverEntity = new GameLoverEntity(){{setId(new GameLoverId(1L,1L));setLove(true);}};
        when(gameLoverRepository.findById(new GameLoverId(1L,1L))).thenReturn(Optional.of(gameLoverEntity));

        GameLoverEntity gameLoverEntity1 = new GameLoverEntity(){{setLove(false);setId(new GameLoverId(1L,1L));}};
        when(gameLoverRepository.save(any())).thenReturn(gameLoverEntity1);


        //test toggleLove service
        GameLoverModel gameLoverModel = gameLoverService.toggleLove(new GameLoverId(1L, 1L));
        assertThat(gameLoverModel).isNotNull();
        assertThat(gameLoverModel.getLove()).isFalse();
    }
    @Test
    public void findAllByPlayerId_passPlayerId1ThenShouldReturn3Games() {
        //mock db
        List<GameLoverEntity> gameLoverEntities = new ArrayList<>();
        gameLoverEntities.add(new GameLoverEntity(){{setId(new GameLoverId(1L,1L));setLove(true);}});
        gameLoverEntities.add(new GameLoverEntity(){{setId(new GameLoverId(2L,1L));setLove(true);}});
        gameLoverEntities.add(new GameLoverEntity(){{setId(new GameLoverId(3L,1L));setLove(true);}});
        when(gameLoverRepository.findAllByPlayerId(1L, PageRequest.of(0, 10))).thenReturn(new PageImpl<>(gameLoverEntities));

        //test findAllByPlayerId service
        Page<GameModel> gameModels = gameLoverService.findAllByPlayerId(1L, PageRequest.of(0, 10));
        assertThat(gameModels).isNotNull().isNotEmpty().size().isEqualTo(3);
        assertThat(gameModels.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(gameModels.getContent().get(1).getId()).isEqualTo(2L);
        assertThat(gameModels.getContent().get(2).getId()).isEqualTo(3L);
    }
    @Test
    public void findAllByPlayerIdAndLoved_passPlayerId1ThenShouldReturn3Games() {
        //mock db
        List<GameLoverEntity> gameLoverEntities = new ArrayList<>();
        gameLoverEntities.add(new GameLoverEntity(){{setId(new GameLoverId(1L,1L));setLove(true);}});
        gameLoverEntities.add(new GameLoverEntity(){{setId(new GameLoverId(2L,1L));setLove(true);}});
        gameLoverEntities.add(new GameLoverEntity(){{setId(new GameLoverId(3L,1L));setLove(true);}});
        when(gameLoverRepository.findAllByPlayerIdAndLoved(1L, PageRequest.of(0, 10))).thenReturn(new PageImpl<>(gameLoverEntities));

        //test findAllByPlayerIdAndLoved service
        Page<GameModel> gameModelPage = gameLoverService.findAllByPlayerIdAndLoved(1L, PageRequest.of(0, 10));
        assertThat(gameModelPage).isNotNull().isNotEmpty().size().isEqualTo(3);
        assertThat(gameModelPage.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(gameModelPage.getContent().get(1).getId()).isEqualTo(2L);
        assertThat(gameModelPage.getContent().get(2).getId()).isEqualTo(3L);
    }

    @Test
    public void findAllByPlayerIdAndLoved_shouldReturn2LovedGame() {
        //mock db
        List<LovedGameModel> lovedGameModels = new ArrayList<>();
        lovedGameModels.add(new LovedGameModel("Game A",2));
        lovedGameModels.add(new LovedGameModel("Game B",1));
        when(gameLoverService.findAllMostLovedGames(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(lovedGameModels));

        //test findAllByPlayerIdAndLoved service
        Page<LovedGameModel> lovedGameModelPage = gameLoverService.findAllMostLovedGames(PageRequest.of(0, 10));
        assertThat(lovedGameModelPage).isNotNull().isNotEmpty().size().isEqualTo(2);
        assertThat(lovedGameModelPage.getContent().get(0).getTitle()).isEqualTo("Game A");
        assertThat(lovedGameModelPage.getContent().get(0).getCount()).isEqualTo(2);
        assertThat(lovedGameModelPage.getContent().get(1).getTitle()).isEqualTo("Game B");
        assertThat(lovedGameModelPage.getContent().get(1).getCount()).isEqualTo(1);
    }
}
