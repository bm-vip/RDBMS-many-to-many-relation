package comeon.group.gameloveservice.controller.impl;

import comeon.group.gameloveservice.entity.GameLoverId;
import comeon.group.gameloveservice.model.GameLoverModel;
import comeon.group.gameloveservice.model.GameModel;
import comeon.group.gameloveservice.model.LovedGameModel;
import comeon.group.gameloveservice.service.GameLoverService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Behrooz.Mohamadi on 14/08/2022.
 */
@RestController
@Tag(name = "Game Lover Rest Service v1")
@RequestMapping(value = "/api/v1/game-lover")
@Validated
public class GameLoverRestController {

    private GameLoverService gameLoverService;

    public GameLoverRestController(GameLoverService service) {
        this.gameLoverService = service;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<GameLoverModel> save(@Valid @RequestBody GameLoverModel model) {
        return ResponseEntity.ok(gameLoverService.save(model));
    }

    @PutMapping("/toggle-love/{gameId}/{playerId}")
    @ResponseBody
    public ResponseEntity<GameLoverModel> toggleLove(@PathVariable Long gameId, @PathVariable Long playerId) {
        return ResponseEntity.ok(gameLoverService.toggleLove(new GameLoverId(gameId, playerId)));
    }

    @DeleteMapping("/{gameId}/{playerId}")
    @ResponseBody
    public ResponseEntity<Void> deleteById(@PathVariable Long gameId, @PathVariable Long playerId) {
        gameLoverService.deleteById(new GameLoverId(gameId, playerId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = {"/find-games/{playerId}"})
    ResponseEntity<Page<GameModel>> findAllByPlayerId(@PathVariable Long playerId, @NotNull final Pageable pageable) {
        return ResponseEntity.ok(gameLoverService.findAllByPlayerId(playerId, pageable));
    }

    @GetMapping(value = {"/find-games-loved/{playerId}"})
    ResponseEntity<Page<GameModel>> findAllByPlayerIdAndLoved(@PathVariable Long playerId, @NotNull final Pageable pageable) {
        return ResponseEntity.ok(gameLoverService.findAllByPlayerIdAndLoved(playerId, pageable));
    }

    @GetMapping(value = {"/find-games-most-loved"})
    ResponseEntity<Page<LovedGameModel>> findAllMostLovedGames(@NotNull final Pageable pageable) {
        return ResponseEntity.ok(gameLoverService.findAllMostLovedGames(pageable));
    }

}
