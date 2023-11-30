package pl.edu.agh.productivitypal.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.productivitypal.config.Jwt;
import pl.edu.agh.productivitypal.dto.CategoryDto;
import pl.edu.agh.productivitypal.dto.SettingsLoginDto;
import pl.edu.agh.productivitypal.model.AppUser;
import pl.edu.agh.productivitypal.model.Category;
import pl.edu.agh.productivitypal.repository.AppUserRepository;
import pl.edu.agh.productivitypal.repository.CategoryRepository;

@Slf4j
@Service
public class SettingsService {

    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final CalendarService calendarService;
    private final TaskService taskService;
    private final EnergyLevelInfoService energyLevelInfoService;
    private final CategoryRepository categoryRepository;


    public SettingsService(AppUserService appUserService, PasswordEncoder passwordEncoder, AppUserRepository appUserRepository, CalendarService calendarService, TaskService taskService, EnergyLevelInfoService energyLevelInfoService, CategoryRepository categoryRepository) {
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.appUserRepository = appUserRepository;
        this.calendarService = calendarService;
        this.taskService = taskService;
        this.energyLevelInfoService = energyLevelInfoService;
        this.categoryRepository = categoryRepository;
    }

    public void changeLoginData(Jwt jwt, SettingsLoginDto settingsLoginDto) {
        AppUser currentUser = appUserService.getUserByEmail(jwt);
        log.info("Current user: id {} name {}", currentUser.getId(), currentUser.getUsername());

        if (settingsLoginDto.getEmail() == null && settingsLoginDto.getPassword() == null) {
            log.info("No data to change.");
            return;
        }
        if (settingsLoginDto.getEmail() != null) {
            currentUser.setEmail(settingsLoginDto.getEmail());
        }
        if (settingsLoginDto.getPassword() != null) {
            currentUser.setPassword(passwordEncoder.encode(settingsLoginDto.getPassword()));
        }
        appUserRepository.save(currentUser);
    }

    @Transactional
    public void deleteAccount(Jwt jwt) {
        AppUser currentUser = appUserService.getUserByEmail(jwt);
        log.info("Current user: id {} name {}", currentUser.getId(), currentUser.getUsername());

        log.info("Deleting user: id {} name {}", currentUser.getId(), currentUser.getUsername());

        calendarService.deleteCalendar(jwt, currentUser.getCalendar().get(0));
        taskService.deleteAllTasksOfCurrentUser(currentUser);

        appUserRepository.delete(currentUser);
    }

    public void changeCategories(Jwt jwt, CategoryDto categoryDto) {
        AppUser currentUser = appUserService.getUserByEmail(jwt);
        log.info("Current user: id {} name {}", currentUser.getId(), currentUser.getUsername());

        if (categoryDto.getBeige() == null && categoryDto.getGreen() == null && categoryDto.getAccent() == null && categoryDto.getGrey() == null) {
            log.info("No data to change.");
            return;
        }

        if (categoryDto.getBeige() != null){
            Category c1 = categoryRepository.findByDefaultNameAndAppUserId("beige", currentUser.getId());
            c1.setCustomName(categoryDto.getBeige());
            categoryRepository.save(c1);
        }

        if (categoryDto.getGreen() != null){
            Category c2 = categoryRepository.findByDefaultNameAndAppUserId("green", currentUser.getId());
            c2.setCustomName(categoryDto.getGreen());
            categoryRepository.save(c2);
        }

        if (categoryDto.getAccent() != null){
            Category c3 = categoryRepository.findByDefaultNameAndAppUserId("accent", currentUser.getId());
            c3.setCustomName(categoryDto.getAccent());
            categoryRepository.save(c3);
        }

        if (categoryDto.getGrey() != null){
            Category c4 = categoryRepository.findByDefaultNameAndAppUserId("grey", currentUser.getId());
            c4.setCustomName(categoryDto.getGrey());
            categoryRepository.save(c4);
        }
    }
}
