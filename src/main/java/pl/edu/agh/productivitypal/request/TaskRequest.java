package pl.edu.agh.productivitypal.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.productivitypal.enums.Difficulty;
import pl.edu.agh.productivitypal.enums.Likeliness;

@Getter
@Setter
public class TaskRequest {
    private int priority;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    private Likeliness likeliness;

    private String category;

}



