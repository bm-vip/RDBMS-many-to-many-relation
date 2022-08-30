package comeon.group.gameloveservice.controller.impl;

import comeon.group.gameloveservice.controller.LogicalDeletedRestController;
import comeon.group.gameloveservice.model.GameModel;
import comeon.group.gameloveservice.service.GameService;
import comeon.group.gameloveservice.service.LogicalDeletedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Behrooz.Mohamadi on 14/08/2022.
 */
@RestController
@Tag(name = "Game Rest Service v1")
@RequestMapping(value = "/api/v1/game")
@Validated
public class GameRestController extends BaseRestControllerImpl<GameModel, Long> implements LogicalDeletedRestController<Long> {

    private GameService gameService;

    public GameRestController(GameService service) {
        super(service, GameModel.class);
        this.gameService = service;
    }


    @Override
    public LogicalDeletedService<Long> getService() {
        return gameService;
    }
}
