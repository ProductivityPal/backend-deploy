package pl.edu.agh.productivitypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.productivitypal.model.AppUser;
import pl.edu.agh.productivitypal.model.Calendar;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    List<Calendar> findAllByAppUserId(Integer id);
    Calendar findByNameAndAppUser(String name, AppUser appUser);
}
