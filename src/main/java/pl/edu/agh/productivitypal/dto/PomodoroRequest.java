package pl.edu.agh.productivitypal.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PomodoroRequest {
    private final int taskId;
    private final long completionTime;
}
