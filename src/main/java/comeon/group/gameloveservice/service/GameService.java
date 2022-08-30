package comeon.group.gameloveservice.service;

import comeon.group.gameloveservice.model.GameModel;

public interface GameService extends BaseService<GameModel, Long> , LogicalDeletedService<Long> {
}
