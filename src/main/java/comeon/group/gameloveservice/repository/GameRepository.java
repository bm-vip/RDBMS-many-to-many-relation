package comeon.group.gameloveservice.repository;

import comeon.group.gameloveservice.entity.GameEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends BaseRepository<GameEntity, Long> {
    @Query(value = "select g.* from tbl_game g where exists (select 1 from tbl_game_lover gl where gl.game_id = g.id and gl.love = true group by gl.game_id order by count(gl.game_id) desc)",
            countQuery = "select count(g.id) from tbl_game g where exists (select 1 from tbl_game_lover gl where gl.game_id = g.id and gl.love = true group by gl.game_id)",
            nativeQuery = true)
    Page<GameEntity> findAllMostLovedGame(Pageable pageable);
}