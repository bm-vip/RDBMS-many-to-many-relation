package comeon.group.gameloveservice.mapping;

import comeon.group.gameloveservice.entity.GameLoverEntity;
import comeon.group.gameloveservice.model.GameLoverModel;
import comeon.group.gameloveservice.model.GameModel;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",uses = {GameMapper.class, PlayerMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
public interface GameLoverMapper extends BaseMapper<GameLoverModel, GameLoverEntity> {
    @Mappings({
            @Mapping(target = "id", source = "id.game.id"),
            @Mapping(target = "title", source = "id.game.title"),
    })
    GameModel toGameModel(final GameLoverEntity entity);
}
