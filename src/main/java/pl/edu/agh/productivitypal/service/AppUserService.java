package pl.edu.agh.productivitypal.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.agh.productivitypal.config.Jwt;
import pl.edu.agh.productivitypal.config.JwtService;
import pl.edu.agh.productivitypal.enums.EnergyLevel;
import pl.edu.agh.productivitypal.model.AppUser;
import pl.edu.agh.productivitypal.repository.AppUserRepository;

@Slf4j
@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;
    private final EnergyLevelInfoService energyLevelInfoService;

    public AppUserService(AppUserRepository appUserRepository, JwtService jwtService, EnergyLevelInfoService energyLevelInfoService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
        this.energyLevelInfoService = energyLevelInfoService;
    }

    public void addUser(AppUser appUser){
        appUserRepository.save(appUser);
    }

    public void updateEnergyLevel(Jwt jwt, EnergyLevel energyLevel){
        AppUser appUser = getUserByEmail(jwt);
        log.info("Current user: id {} name {}", appUser.getId(), appUser.getUsername());

        if (energyLevel != null){
            appUser.setCurrentEnergyLevel(energyLevel);
            appUserRepository.save(appUser);
            energyLevelInfoService.addEnergyLevelInfo(energyLevel, appUser);
        }
    }

    public AppUser getUserByEmail(Jwt jwt) {
        String email = jwtService.extractEmail(jwt.getToken());
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email" + email + "not found: "));
    }
}
