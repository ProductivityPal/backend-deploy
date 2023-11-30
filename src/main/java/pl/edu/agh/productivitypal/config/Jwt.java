package pl.edu.agh.productivitypal.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Jwt implements Converter<String, Jwt> {

    @Getter
    private String token;

    private final static String PREFIX = "Bearer ";

    @Override
    public Jwt convert(String rawToken) {
        String token = rawToken.replaceFirst(PREFIX, "");
        return new Jwt(token);
    }
}

