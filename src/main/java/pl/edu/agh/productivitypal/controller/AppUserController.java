package pl.edu.agh.productivitypal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.productivitypal.config.Jwt;
import pl.edu.agh.productivitypal.enums.EnergyLevel;
import pl.edu.agh.productivitypal.model.AppUser;
import pl.edu.agh.productivitypal.service.AppUserService;

import static pl.edu.agh.productivitypal.config.SecurityConstant.AUTHORIZATION_HEADER;

@RestController
@RequestMapping(value = "/user")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/energyLevel/{energyLevel}")
    public ResponseEntity<String> updateEnergyLevel(@PathVariable EnergyLevel energyLevel, @RequestHeader(AUTHORIZATION_HEADER) Jwt jwt) {
        appUserService.updateEnergyLevel(jwt, energyLevel);
        return ResponseEntity.ok("Energy level was updated");
    }

}
