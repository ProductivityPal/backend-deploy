package pl.edu.agh.productivitypal.model;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.agh.productivitypal.enums.EnergyLevel;
import pl.edu.agh.productivitypal.enums.Role;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonValue
    private Integer id;
    private String username;
    private String password;
    private String email;
    private boolean isEmailVerified;

    @Enumerated(EnumType.STRING)
    private EnergyLevel currentEnergyLevel;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "appUser")
    private List<Task> task;

    @OneToMany(mappedBy = "appUser")
    private List<Calendar> calendar;

    @OneToMany(mappedBy = "appUser")
    private List<EnergyLevelInfo> energyLevelInfo;

    private DateTime created;
    private DateTime updated;

    public void setIsEmailVerified(boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    @PrePersist
    public void prePersist() {
        created = new DateTime();
        updated = new DateTime();
    }

    @PreUpdate
    public void preUpdate() {
        updated = new DateTime();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
