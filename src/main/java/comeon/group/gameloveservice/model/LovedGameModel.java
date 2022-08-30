package comeon.group.gameloveservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LovedGameModel {
    private String title;
    private long count;
}
