package pl.edu.agh.productivitypal.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.productivitypal.config.JwtService;
import pl.edu.agh.productivitypal.enums.EnergyLevel;
import pl.edu.agh.productivitypal.enums.Role;
import pl.edu.agh.productivitypal.model.AppUser;
import pl.edu.agh.productivitypal.model.Calendar;
import pl.edu.agh.productivitypal.repository.AppUserRepository;
import pl.edu.agh.productivitypal.repository.CalendarRepository;
import pl.edu.agh.productivitypal.service.AppUserService;
import pl.edu.agh.productivitypal.service.CalendarService;
import pl.edu.agh.productivitypal.service.CategoryService;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository userRepository;
    private final AppUserService appUserService;
    private final CalendarRepository calendarRepository;
    private final CategoryService categoryService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        AppUser user = AppUser.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .role(Role.USER)
                .currentEnergyLevel(EnergyLevel.MEDIUM)
                .build();

        appUserService.addUser(user);

        categoryService.addDefaultCategories(user);

        Calendar calendar = new Calendar();
        calendar.setAppUser(user);
        calendar.setName("Default");

        calendarRepository.save(calendar);

        var jwtToken = jwtService.generateToken(user);

        log.info("User {} registered successfully", user.getUsername());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .calendarId(calendar.getId())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest registerRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getEmail(),
                        registerRequest.getPassword()
                )
        );
        AppUser user = userRepository.findByEmail(registerRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        log.info("User {} authenticated successfully", user.getUsername());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .calendarId(user.getCalendar().get(0).getId())
                .build();
    }
}
