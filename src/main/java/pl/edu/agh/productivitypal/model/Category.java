package pl.edu.agh.productivitypal.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String defaultName;
    private String customName;

    @ManyToOne
    private AppUser appUser;
}
