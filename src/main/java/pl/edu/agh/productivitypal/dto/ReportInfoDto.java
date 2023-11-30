package pl.edu.agh.productivitypal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import pl.edu.agh.productivitypal.model.Task;

import java.util.List;

@Builder
@Getter
public class ReportInfoDto {
    private final long done;
    private final long undone;
    private final List<CategoryInfoDto> categories;
}
