package comeon.group.gameloveservice.mapping;

import comeon.group.gameloveservice.entity.GameEntity;
import comeon.group.gameloveservice.model.GameModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",uses = {GameLoverMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
public interface GameMapper extends BaseMapper<GameModel, GameEntity> {
}
