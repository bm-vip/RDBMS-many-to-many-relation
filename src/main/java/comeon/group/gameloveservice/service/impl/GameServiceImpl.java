package comeon.group.gameloveservice.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import comeon.group.gameloveservice.entity.GameEntity;
import comeon.group.gameloveservice.entity.QGameEntity;
import comeon.group.gameloveservice.mapping.GameMapper;
import comeon.group.gameloveservice.model.GameModel;
import comeon.group.gameloveservice.repository.GameRepository;
import comeon.group.gameloveservice.service.GameService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GameServiceImpl extends BaseServiceImpl<GameModel, GameEntity, Long> implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper) {
        super(gameRepository, gameMapper);
        this.gameRepository = gameRepository;
    }

    @Override
    public JpaRepository<GameEntity,Long> getRepository() {
        return gameRepository;
    }

    @Override
    public Predicate queryBuilder(GameModel filter) {
        BooleanBuilder builder = new BooleanBuilder();
        QGameEntity path = QGameEntity.gameEntity;

        if (StringUtils.hasText(filter.getTitle()))
            builder.and(path.title.contains(filter.getTitle()));

        return builder;
    }
}
