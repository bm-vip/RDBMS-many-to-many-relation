package comeon.group.gameloveservice.service;

import comeon.group.gameloveservice.model.PlayerModel;

public interface PlayerService extends BaseService<PlayerModel, Long> , LogicalDeletedService<Long> {
}
