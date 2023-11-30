package pl.edu.agh.productivitypal.config.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties("verification.email")
public class EmailVerificationConfiguration {
    private long otpExpiration;
    private String templatePath;
    private String subject;
}
