package pl.edu.agh.productivitypal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.productivitypal.enums.EnergyLevel;
import pl.edu.agh.productivitypal.model.AppUser;
import pl.edu.agh.productivitypal.model.EnergyLevelInfo;
import pl.edu.agh.productivitypal.repository.EnergyLevelInfoRepository;

import java.time.LocalDateTime;

@Service
public class EnergyLevelInfoService {

    private final EnergyLevelInfoRepository energyLevelInfoRepository;

    public EnergyLevelInfoService(EnergyLevelInfoRepository energyLevelInfoRepository) {
        this.energyLevelInfoRepository = energyLevelInfoRepository;
    }

    public void addEnergyLevelInfo(EnergyLevel energyLevel, AppUser appUser){
        EnergyLevelInfo info = EnergyLevelInfo.builder()
                .energyLevel(energyLevel)
                .notificationTime(LocalDateTime.now())
                .appUser(appUser)
                .build();
        energyLevelInfoRepository.save(info);
    }

    @Transactional
    public void deleteAllEnergyLevelInfoOfCurrentUser(AppUser appUser){
        energyLevelInfoRepository.deleteAllByAppUser(appUser);
    }
}
