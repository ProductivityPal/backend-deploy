package pl.edu.agh.productivitypal.config.email;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties("verified.email")
public class VerifiedEmailConfiguration {
    private String url;
}

