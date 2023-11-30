package pl.edu.agh.productivitypal.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SettingsLoginDto {
    private final String email;
    private final String password;
}
