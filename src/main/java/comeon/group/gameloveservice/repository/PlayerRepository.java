package comeon.group.gameloveservice.repository;

import comeon.group.gameloveservice.entity.PlayerEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends BaseRepository<PlayerEntity, Long> {
}