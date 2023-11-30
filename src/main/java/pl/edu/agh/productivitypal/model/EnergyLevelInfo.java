package pl.edu.agh.productivitypal.model;

import jakarta.persistence.*;
import lombok.*;
import pl.edu.agh.productivitypal.enums.EnergyLevel;

import java.time.LocalDateTime;

@Entity
@Table(name = "energy_level_info")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyLevelInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private EnergyLevel energyLevel;

    private LocalDateTime notificationTime;

    @ManyToOne
    private AppUser appUser;
}
