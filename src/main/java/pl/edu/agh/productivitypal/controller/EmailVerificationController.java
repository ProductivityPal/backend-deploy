package pl.edu.agh.productivitypal.controller;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.productivitypal.config.Jwt;
import pl.edu.agh.productivitypal.dto.EmailVerificationDto;
import pl.edu.agh.productivitypal.service.EmailVerificationService;

import static pl.edu.agh.productivitypal.config.SecurityConstant.AUTHORIZATION_HEADER;

@RestController
@RequestMapping(value = "/email/verification")
@AllArgsConstructor
public class EmailVerificationController {
    private final EmailVerificationService emailVerificationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestHeader(AUTHORIZATION_HEADER) Jwt jwt) throws MessagingException {
        emailVerificationService.sendVerificationEmail(jwt);
        return ResponseEntity.ok("Email was sent. Check your inbox and click the link to verify your account.");
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody EmailVerificationDto emailVerificationDto) {
        boolean isVerified = emailVerificationService.verifyCode(emailVerificationDto);
        return isVerified ? ResponseEntity.ok("User was verified.") : ResponseEntity.badRequest().build();
    }
}
