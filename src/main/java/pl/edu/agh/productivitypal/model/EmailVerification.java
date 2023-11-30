package pl.edu.agh.productivitypal.model;

import jakarta.persistence.*;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.stereotype.Indexed;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "email_verification")
@NoArgsConstructor
@Indexed
public class EmailVerification  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    private DateTime created;

    private DateTime updated;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "is_active", columnDefinition = "boolean default true", nullable = false)
    private boolean isActive = true;

    @PrePersist
    public void prePersist() {
        created = new DateTime();
        updated = new DateTime();
    }

    @PreUpdate
    public void preUpdate() {
        updated = new DateTime();
    }
}
