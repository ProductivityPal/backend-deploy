package pl.edu.agh.productivitypal.service;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.edu.agh.productivitypal.config.ProductivityPalConfiguration;
import pl.edu.agh.productivitypal.config.email.EmailVerificationConfiguration;
import pl.edu.agh.productivitypal.config.email.VerifiedEmailConfiguration;
import pl.edu.agh.productivitypal.config.Jwt;
import pl.edu.agh.productivitypal.dto.EmailVerificationDto;
import pl.edu.agh.productivitypal.model.AppUser;
import pl.edu.agh.productivitypal.model.EmailVerification;
import pl.edu.agh.productivitypal.repository.AppUserRepository;
import pl.edu.agh.productivitypal.repository.EmailVerificationRepository;
import pl.edu.agh.productivitypal.util.OtpGenerator;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class EmailVerificationService {
    private final EmailService emailService;
    private final AppUserService userService;
    private final EmailVerificationRepository emailVerificationRepository;
    private final AppUserRepository userRepository;
    private final VerifiedEmailConfiguration verifiedEmailConfiguration;
    private final ProductivityPalConfiguration productivityPalConfiguration;
    private final EmailVerificationConfiguration emailVerificationConfiguration;
    private final Duration otpExpiration;

    public EmailVerificationService(
            EmailService emailService,
            AppUserService userService,
            EmailVerificationRepository emailVerificationRepository,
            AppUserRepository userRepository,
            VerifiedEmailConfiguration verifiedEmailConfiguration,
            ProductivityPalConfiguration productivityPalConfiguration,
            EmailVerificationConfiguration emailVerificationConfiguration) {
        this.emailService = emailService;
        this.userService = userService;
        this.emailVerificationRepository = emailVerificationRepository;
        this.userRepository = userRepository;
        this.verifiedEmailConfiguration = verifiedEmailConfiguration;
        this.productivityPalConfiguration = productivityPalConfiguration;
        this.emailVerificationConfiguration = emailVerificationConfiguration;
        otpExpiration = Duration.millis(emailVerificationConfiguration.getOtpExpiration());
    }

    @Async
    public void sendVerificationEmail(Jwt jwt) throws MessagingException {
        AppUser user = userService.getUserByEmail(jwt);
        String message = addDataToEmailTemplate(user.getEmail(), getOtpCode(user));
        emailService.send(user.getEmail(), emailVerificationConfiguration.getSubject(), message, true);
    }

    public boolean verifyCode(EmailVerificationDto code) {
        AppUser user = userRepository.findByEmail(code.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("User " + code.getEmail() + "not found"));

        Optional<EmailVerification> emailVerification = emailVerificationRepository
                .findByUserIdAndCodeAndIsActiveIsTrueAndCreatedLessThan(
                        user.getId(),
                        code.getCode(),
                        new DateTime().plus(otpExpiration));

        if (emailVerification.isPresent()) {
            user.setIsEmailVerified(true);
            userRepository.save(user);
            deactivateCodes(user);
            return true;
        }
        return false;
    }

    private String addDataToEmailTemplate(String email, String code) {
        String emailTemplate = loadEmailTemplate();
        String verificationUrl = verifiedEmailConfiguration.getUrl() + "?email=" + email + "&code=" + code;
        return emailTemplate.replace("[VERIFICATION_URL]", verificationUrl);
    }

    private String getOtpCode(AppUser user) {
        String code = OtpGenerator.generateCode();
        if (emailVerificationRepository.existsByCode(code)) {
            return getOtpCode(user);
        } else {
            return createOtpCode(user, code);
        }
    }

    private String createOtpCode(AppUser user, String code) {
        EmailVerification emailVerification = EmailVerification.builder()
                .user(user)
                .code(code)
                .isActive(true)
                .build();
        deactivateCodes(user);
        emailVerificationRepository.save(emailVerification);
        return code;
    }

    private void deactivateCodes(AppUser user) {
        List<EmailVerification> deactivateVerifications = emailVerificationRepository
                .findAllByUserIdAndIsActiveIsTrue(user.getId())
                .stream()
                .peek(u -> u.setActive(false))
                .toList();

        emailVerificationRepository.saveAll(deactivateVerifications);
    }

    private String loadEmailTemplate() {
        InputStream fileStream = this.getClass().getResourceAsStream(emailVerificationConfiguration.getTemplatePath());
        String emailTemplateScanner = new Scanner(fileStream, "UTF-8").useDelimiter("\\A").next();
        return emailTemplateScanner.replace("[APP_NAME]", productivityPalConfiguration.getName());
    }
}
