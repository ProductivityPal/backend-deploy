package pl.edu.agh.productivitypal.repository;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.productivitypal.model.AppUser;
import pl.edu.agh.productivitypal.model.EnergyLevelInfo;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnergyLevelInfoRepository extends JpaRepository<EnergyLevelInfo, Integer> {

    List<EnergyLevelInfo> findAllByAppUserIdAndNotificationTimeBetween(Integer id, LocalDateTime startDate, LocalDateTime endDate);

    void deleteAllByAppUser(AppUser appUser);
}
