package pl.edu.agh.productivitypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.productivitypal.model.CalendarTask;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarTaskRepository extends JpaRepository<CalendarTask, Integer> {
    List<CalendarTask> findAllByCalendarId(Integer id);

    Optional<CalendarTask> findByIdAndCalendarId(Integer id, Integer calendarId);

    Optional<CalendarTask> findByCalendarIdAndTaskId(Integer calendarId, Integer taskId);
    @Query(value = "SELECT ct.* " +
        "FROM calendar_task ct " +
        "JOIN task t ON ct.task_id = t.id " +
        "JOIN app_user au ON t.app_user_id = au.id " +
        "WHERE ct.start_date >= :startDate " +
        "AND (ct.end_date IS NULL OR ct.end_date <= CURRENT_TIMESTAMP) " +
        "AND au.id = :userId ", nativeQuery = true)
    List<CalendarTask> findAllByUserIdAndGivenPeriodOfTime(@Param("userId") Integer userId,
                                                           @Param("startDate") LocalDateTime startDate);
}
