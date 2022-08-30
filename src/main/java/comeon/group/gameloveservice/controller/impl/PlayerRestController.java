package comeon.group.gameloveservice.controller.impl;

import comeon.group.gameloveservice.controller.LogicalDeletedRestController;
import comeon.group.gameloveservice.model.GameModel;
import comeon.group.gameloveservice.model.PlayerModel;
import comeon.group.gameloveservice.service.GameService;
import comeon.group.gameloveservice.service.LogicalDeletedService;
import comeon.group.gameloveservice.service.PlayerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Behrooz.Mohamadi on 14/08/2022.
 */
@RestController
@Tag(name = "Player Rest Service v1")
@RequestMapping(value = "/api/v1/player")
@Validated
public class PlayerRestController extends BaseRestControllerImpl<PlayerModel, Long> implements LogicalDeletedRestController<Long> {

    private PlayerService playerService;

    public PlayerRestController(PlayerService service) {
        super(service, PlayerModel.class);
        this.playerService = service;
    }


    @Override
    public LogicalDeletedService<Long> getService() {
        return playerService;
    }
}
