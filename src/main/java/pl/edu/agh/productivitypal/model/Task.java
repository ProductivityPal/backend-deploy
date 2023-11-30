package pl.edu.agh.productivitypal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.agh.productivitypal.enums.Difficulty;
import pl.edu.agh.productivitypal.enums.Likeliness;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private int priority;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    @Enumerated(EnumType.STRING)
    private Likeliness likeliness;
    private LocalDateTime deadline;
    @JsonProperty("time_estimate")
    private Long timeEstimate;
    @JsonProperty("completion_time")
    private Long completionTime;
    private boolean isSubtask;
    private boolean isParent;
    private boolean isCompleted;
    private String category;

    @JsonProperty("parent_id")
    private Integer parentId;

    @Transient
    private double priorityScore;

    @ManyToOne
    @JoinColumn(name = "app_user_id") // Wskazuje na kolumnę w tabeli task, która przechowuje klucz obcy do użytkownika
    private AppUser appUser;

    public Task(Integer id, String name, String description, int priority, Difficulty difficulty, Likeliness likeliness, LocalDateTime deadline, Long timeEstimate, Long completionTime, boolean isSubtask, boolean isParent, boolean isCompleted, Integer parentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.difficulty = difficulty;
        this.likeliness = likeliness;
        this.deadline = deadline;
        this.timeEstimate = timeEstimate;
        this.completionTime = completionTime;
        this.isSubtask = isSubtask;
        this.isParent = isParent;
        this.isCompleted = isCompleted;
        this.parentId = parentId;

    }
}


