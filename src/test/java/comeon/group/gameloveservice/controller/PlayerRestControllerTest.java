package comeon.group.gameloveservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import comeon.group.gameloveservice.model.PlayerModel;
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

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlayerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @Commit
    void save_shouldSavePlayerModelToDatabase() throws Exception {
        PlayerModel model = new PlayerModel() {{
            setFirstName("Player D");
            setLastName("D");
            setEmail("d@d.d");
            setBirthDate(new Date());
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(post("/api/v1/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.firstName").value("Player D"))
        ;
    }
    @Test
    @Order(2)
    @Commit
    void update_shouldUpdatePlayerModelToDatabase() throws Exception {
        PlayerModel model = new PlayerModel() {{
            setId(4L);
            setEmail("test@d.d");
        }};
        String json = objectMapper.writeValueAsString(model);

        mockMvc.perform(patch("/api/v1/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.email").value("test@d.d"))
        ;
    }

    @Test
    @Order(3)
    void deleteById_shouldDeleteByIdFromDatabase() throws Exception {
        mockMvc.perform(delete("/api/v1/player/{id}",4L))
                .andExpect(status().isNoContent());
    }

    @Test
    void save_shouldThrowBadRequestError() throws Exception {
        mockMvc.perform(post("/api/v1/player").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void findById_shouldReturnPlayerModel() throws Exception {
        mockMvc.perform(get("/api/v1/player/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Player A"));
    }

    @Test
    void findById_shouldThrowNotFoundError() throws Exception {
        mockMvc.perform(get("/api/v1/player/{id}", 44L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll_shouldReturnPageablePlayerModels() throws Exception {
        String filter = objectMapper.writeValueAsString(new PlayerModel(){{setFirstName("Player");}});

        mockMvc.perform(get("/api/v1/player").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.content", Matchers.hasSize(3)));
    }

    @Test
    void countAll_shouldReturnTotalNumberOfPlayers() throws Exception {
        String filter = objectMapper.writeValueAsString(new PlayerModel(){{setFirstName("Player");}});

        mockMvc.perform(get("/api/v1/player/count").param("model",filter))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }
}
