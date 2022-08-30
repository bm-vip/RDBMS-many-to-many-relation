package comeon.group.gameloveservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import comeon.group.gameloveservice.model.GameModel;
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
class GameRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @Commit
    void save_shouldSaveGameModelToDatabase() throws Exception {
        GameModel model = new GameModel() {{
            setTitle("Game D");
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(post("/api/v1/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.title").value("Game D"))
        ;
    }
    @Test
    @Order(2)
    @Commit
    void update_shouldUpdateGameModelToDatabase() throws Exception {
        GameModel model = new GameModel() {{
            setId(4L);
            setTitle("Game E");
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(patch("/api/v1/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.title").value("Game E"))
        ;
    }

    @Test
    @Order(3)
    void deleteById_shouldDeleteByIdFromDatabase() throws Exception {
        mockMvc.perform(delete("/api/v1/game/{id}",4L))
                .andExpect(status().isNoContent());
    }

    @Test
    void save_shouldThrowBadRequestError() throws Exception {
        mockMvc.perform(post("/api/v1/game").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void findById_shouldReturnGameModel() throws Exception {
        mockMvc.perform(get("/api/v1/game/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Game A"));
    }

    @Test
    void findById_shouldThrowNotFoundError() throws Exception {
        mockMvc.perform(get("/api/v1/game/{id}", 44L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll_shouldReturnPageableGameModels() throws Exception {
        String filter = objectMapper.writeValueAsString(new GameModel(){{setTitle("Game");}});

        mockMvc.perform(get("/api/v1/game").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.content", Matchers.hasSize(3)));
    }

    @Test
    void countAll_shouldReturnTotalNumberOfPlayers() throws Exception {
        String filter = objectMapper.writeValueAsString(new GameModel(){{setTitle("Game");}});

        mockMvc.perform(get("/api/v1/game/count").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }
}
