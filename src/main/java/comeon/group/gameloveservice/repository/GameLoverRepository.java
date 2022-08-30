package comeon.group.gameloveservice.repository;

import comeon.group.gameloveservice.entity.GameLoverEntity;
import comeon.group.gameloveservice.entity.GameLoverId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLoverRepository extends BaseRepository<GameLoverEntity, GameLoverId> {
    @Query("select g from GameLoverEntity g where g.id.player.id=:playerId")
    Page<GameLoverEntity> findAllByPlayerId(Long playerId, Pageable pageable);

    @Query("select g from GameLoverEntity g where g.id.player.id=:playerId and g.love=true")
    Page<GameLoverEntity> findAllByPlayerIdAndLoved(Long playerId, Pageable pageable);

}