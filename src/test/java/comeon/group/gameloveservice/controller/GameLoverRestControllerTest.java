package comeon.group.gameloveservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import comeon.group.gameloveservice.entity.GameLoverId;
import comeon.group.gameloveservice.model.GameLoverModel;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameLoverRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @Commit
    void save_shouldSaveGameLoverModelToDatabase() throws Exception {
        GameLoverModel model = new GameLoverModel() {{
            setId(new GameLoverId(3L,3L));
            setLove(true);
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(post("/api/v1/game-lover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.game.id").value(3))
                .andExpect(jsonPath("$.id.player.id").value(3))
                .andExpect(jsonPath("$.love").value(true))
        ;
    }

    @Test
    @Order(2)
    @Commit
    void toggleLove_shouldUpdateGameLoverModelToDatabase() throws Exception {
        mockMvc.perform(put("/api/v1/game-lover/toggle-love/{gameId}/{playerId}",3L,3L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.game.id").value(3))
                .andExpect(jsonPath("$.id.player.id").value(3))
                .andExpect(jsonPath("$.love").value(false))
        ;
    }

    @Test
    @Order(3)
    void deleteById_shouldDeleteByIdFromDatabase() throws Exception {
        mockMvc.perform(delete("/api/v1/game-lover/{gameId}/{playerId}",3L,3L))
                .andExpect(status().isNoContent());
    }

    @Test
    void save_shouldThrowBadRequestError() throws Exception {
        mockMvc.perform(post("/api/v1/game-lover").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void findAllByPlayerId_shouldReturnPageableGameModels() throws Exception {
        mockMvc.perform(get("/api/v1/game-lover/find-games/{playerId}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect((jsonPath("$.content.*", Matchers.hasSize(2))))
                .andExpect(jsonPath("$.content.[0].title").value("Game A"))
                .andExpect(jsonPath("$.content.[1].title").value("Game B"))
        ;
    }

    @Test
    void findAllByPlayerIdAndLoved_shouldReturnPageableGameModels() throws Exception {
        mockMvc.perform(get("/api/v1/game-lover/find-games-loved/{playerId}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect((jsonPath("$.content.*", Matchers.hasSize(2))))
                .andExpect(jsonPath("$.content.[0].title").value("Game A"))
                .andExpect(jsonPath("$.content.[1].title").value("Game B"))
        ;
    }

    @Test
    void findAllMostLovedGames_shouldReturnPageableLovedGameModels() throws Exception {
        mockMvc.perform(get("/api/v1/game-lover/find-games-most-loved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect((jsonPath("$.content.*", Matchers.hasSize(2))))
                .andExpect(jsonPath("$.content.[0].title").value("Game A"))
                .andExpect(jsonPath("$.content.[0].count").value(2))
                .andExpect(jsonPath("$.content.[1].title").value("Game B"))
                .andExpect(jsonPath("$.content.[1].count").value(1))
        ;
    }
}
