package comeon.group.gameloveservice.service;

import comeon.group.gameloveservice.entity.GameLoverId;
import comeon.group.gameloveservice.model.GameLoverModel;
import comeon.group.gameloveservice.model.GameModel;
import comeon.group.gameloveservice.model.LovedGameModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameLoverService extends BaseService<GameLoverModel, GameLoverId> {
    Page<GameModel> findAllByPlayerId(Long playerId, Pageable pageable);
    Page<GameModel> findAllByPlayerIdAndLoved(Long playerId, Pageable pageable);
    Page<LovedGameModel> findAllMostLovedGames(Pageable pageable);
    GameLoverModel toggleLove(GameLoverId id);
}
