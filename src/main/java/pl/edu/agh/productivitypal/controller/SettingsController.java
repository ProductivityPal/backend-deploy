package pl.edu.agh.productivitypal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.productivitypal.config.Jwt;
import pl.edu.agh.productivitypal.dto.CategoryDto;
import pl.edu.agh.productivitypal.dto.SettingsLoginDto;
import pl.edu.agh.productivitypal.service.SettingsService;

import static pl.edu.agh.productivitypal.config.SecurityConstant.AUTHORIZATION_HEADER;

@Slf4j
@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<String> changeLoginData(@RequestHeader(AUTHORIZATION_HEADER) Jwt jwt, @RequestBody SettingsLoginDto settingsLoginDto){
        log.info("Received request to change login data. Data {}", settingsLoginDto);
        settingsService.changeLoginData(jwt, settingsLoginDto);
        return ResponseEntity.ok("Login data was changed successfully.");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestHeader(AUTHORIZATION_HEADER) Jwt jwt){
        settingsService.deleteAccount(jwt);
        return ResponseEntity.ok("Account was deleted successfully.");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/category")
    public ResponseEntity<String> changeCategories(@RequestHeader(AUTHORIZATION_HEADER) Jwt jwt, @RequestBody CategoryDto categoryDto){
        settingsService.changeCategories(jwt, categoryDto);
        return ResponseEntity.ok("Categories were changed successfully.");
    }
}
