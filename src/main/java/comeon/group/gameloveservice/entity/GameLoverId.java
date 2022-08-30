package comeon.group.gameloveservice.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class GameLoverId implements Serializable {
    @NotNull
    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    public GameLoverId(Long gameId, Long playerId){
        this.game = new GameEntity(){{setId(gameId);}};
        this.player = new PlayerEntity(){{setId(playerId);}};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameLoverId)) return false;
        GameLoverId that = (GameLoverId) o;
        return Objects.equals(game.getId(), that.game.getId()) && Objects.equals(player.getId(), that.player.getId());
    }

    @Override
    public String toString() {
        return "GameLoverId{" +
                "gameId=" + game.getId() +
                ", playerId=" + player.getId() +
                '}';
    }
}
