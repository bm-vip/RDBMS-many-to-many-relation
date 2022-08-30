package comeon.group.gameloveservice.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import comeon.group.gameloveservice.entity.GameLoverEntity;
import comeon.group.gameloveservice.entity.GameLoverId;
import comeon.group.gameloveservice.entity.QGameLoverEntity;
import comeon.group.gameloveservice.exception.NotFoundException;
import comeon.group.gameloveservice.mapping.GameLoverMapper;
import comeon.group.gameloveservice.mapping.GameMapper;
import comeon.group.gameloveservice.model.GameLoverModel;
import comeon.group.gameloveservice.model.GameModel;
import comeon.group.gameloveservice.model.LovedGameModel;
import comeon.group.gameloveservice.repository.GameLoverRepository;
import comeon.group.gameloveservice.service.GameLoverService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static comeon.group.gameloveservice.util.MapperHelper.option;

@Service
public class GameLoverServiceImpl extends BaseServiceImpl<GameLoverModel, GameLoverEntity, GameLoverId> implements GameLoverService {

    private final GameLoverRepository gameLoverRepository;
    private final GameLoverMapper gameLoverMapper;
    @PersistenceContext
    private final EntityManager entityManager;

    public GameLoverServiceImpl(GameLoverRepository gameLoverRepository, GameLoverMapper gameLoverMapper,EntityManager entityManager) {
        super(gameLoverRepository, gameLoverMapper);
        this.gameLoverRepository = gameLoverRepository;
        this.gameLoverMapper = gameLoverMapper;
        this.entityManager = entityManager;
    }

    @Override
    public Predicate queryBuilder(GameLoverModel filter) {
        BooleanBuilder builder = new BooleanBuilder();
        QGameLoverEntity path = QGameLoverEntity.gameLoverEntity;

        option(() -> filter.getId())
                .ifPresent(id -> builder.and(path.id.eq(id)));
        option(() -> filter.getLove())
                .ifPresent(love -> builder.and(path.love.eq(love)));
        return builder;
    }

    @Override
    public GameLoverModel save(GameLoverModel model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    @Override
    public Page<GameModel> findAllByPlayerId(Long playerId, Pageable pageable) {
        return gameLoverRepository.findAllByPlayerId(playerId, pageable).map(gameLoverMapper::toGameModel);
    }

    @Override
    public Page<GameModel> findAllByPlayerIdAndLoved(Long playerId, Pageable pageable) {
        return gameLoverRepository.findAllByPlayerIdAndLoved(playerId, pageable).map(gameLoverMapper::toGameModel);
    }

    @Override
    public Page<LovedGameModel> findAllMostLovedGames(Pageable pageable) {
        QGameLoverEntity path = QGameLoverEntity.gameLoverEntity;
        JPAQuery<GameLoverEntity> query = new JPAQuery(entityManager);

        Long totalElements = query.from(path).fetchCount();
        List<LovedGameModel> result = query.select(Projections.constructor(LovedGameModel.class, path.id.game.title, path.id.game.title.count()))
                .where(path.love.eq(true))
                .groupBy(path.id.game.title)
                .orderBy(path.id.game.title.count().desc())
                .limit(pageable.getPageSize()).offset(pageable.getPageNumber())
                .fetch();
        return new PageImpl<>(result, pageable, totalElements);
    }

    @Override
    public GameLoverModel toggleLove(GameLoverId id) {
        GameLoverEntity entity = gameLoverRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("%s not found by id %s", GameLoverEntity.class.getName(), id.toString())));
        entity.setLove(!entity.isLove());
        return gameLoverMapper.toModel(gameLoverRepository.save(entity));
    }
}
