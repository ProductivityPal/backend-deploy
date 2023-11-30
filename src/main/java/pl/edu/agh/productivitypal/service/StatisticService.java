package pl.edu.agh.productivitypal.service;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import pl.edu.agh.productivitypal.config.Jwt;
import pl.edu.agh.productivitypal.dto.CategoryInfoDto;
import pl.edu.agh.productivitypal.dto.ReportInfoDto;
import pl.edu.agh.productivitypal.model.*;
import pl.edu.agh.productivitypal.repository.AppUserRepository;
import pl.edu.agh.productivitypal.repository.CalendarTaskRepository;
import pl.edu.agh.productivitypal.repository.EnergyLevelInfoRepository;
import pl.edu.agh.productivitypal.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StatisticService {

    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;
    private final CalendarTaskRepository calendarTaskRepository;
    private final AppUserService appUserService;
    private final EnergyLevelInfoRepository energyLevelInfoRepository;


    public StatisticService(TaskRepository taskRepository, AppUserRepository appUserRepository, CalendarTaskRepository calendarTaskRepository, AppUserService appUserService, EnergyLevelInfoRepository energyLevelInfoRepository) {
        this.taskRepository = taskRepository;
        this.appUserRepository = appUserRepository;
        this.calendarTaskRepository = calendarTaskRepository;
        this.appUserService = appUserService;
        this.energyLevelInfoRepository = energyLevelInfoRepository;
    }

    public ReportInfoDto getInfoReport(Jwt jwt, LocalDateTime startDate, LocalDateTime endDate) {
        AppUser currentUser = appUserService.getUserByEmail(jwt);
        log.info("Current user: id {} name {}", currentUser.getId(), currentUser.getUsername());

        List<Task> tasks = taskRepository.findAllByAppUserIdAndDeadlineBetween(currentUser.getId(), startDate, endDate);

        int done = 0;
        int undone = 0;

        List<CategoryInfoDto> categories = new ArrayList<>();

        for (Task task : tasks){

            List<CategoryInfoDto> taskCategory = categories.stream().filter(c -> c.getName().equals(task.getCategory())).toList();

            CategoryInfoDto category;
            if (categories.isEmpty() || taskCategory.isEmpty()){
                category = CategoryInfoDto.builder()
                        .name(task.getCategory())
                        .done(0)
                        .undone(0)
                        .averageEstimatedTime(0)
                        .averageCompletionTime(0)
                        .build();
                categories.add(category);
            } else {
                category = taskCategory.get(0);
            }

            if (task.isCompleted()){
                done++;
                category.setDone(category.getDone() + 1);
            } else {
                undone++;
                category.setUndone(category.getUndone() + 1);
            }

            category.setAverageCompletionTime(category.getAverageCompletionTime() + (task.getCompletionTime() == null ? 0 : task.getCompletionTime()));
            category.setAverageEstimatedTime(category.getAverageEstimatedTime() + (task.getTimeEstimate() == null ? 0 : task.getTimeEstimate()));
        }

        for (CategoryInfoDto categoryInfoDto : categories){
            categoryInfoDto.setAverageCompletionTime(categoryInfoDto.getAverageCompletionTime() / (categoryInfoDto.getDone() + categoryInfoDto.getUndone()));
            categoryInfoDto.setAverageEstimatedTime(categoryInfoDto.getAverageEstimatedTime() / (categoryInfoDto.getDone() + categoryInfoDto.getUndone()));
        }

        return ReportInfoDto.builder()
                .done(done)
                .undone(undone)
                .categories(categories)
                .build();
    }

    public List<EnergyLevelInfo> getEnergyLevelReport(Jwt jwt){
        AppUser currentUser = appUserService.getUserByEmail(jwt);
        log.info("Current user: id {} name {}", currentUser.getId(), currentUser.getUsername());

        return energyLevelInfoRepository.findAllByAppUserIdAndNotificationTimeBetween(currentUser.getId(), LocalDateTime.now().plusDays(-7), LocalDateTime.now());
    }

}
