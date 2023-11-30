package pl.edu.agh.productivitypal.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CategoryInfoDto {
    private String name;
    private int done;
    private int undone;
    private double averageEstimatedTime;
    private double averageCompletionTime;

}
