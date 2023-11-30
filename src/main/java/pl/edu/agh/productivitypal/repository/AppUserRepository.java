package pl.edu.agh.productivitypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.edu.agh.productivitypal.model.AppUser;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer>, JpaSpecificationExecutor<AppUser> {
    Optional<AppUser> findById(Integer id);
    Optional<AppUser> findByEmail(String email);
}
