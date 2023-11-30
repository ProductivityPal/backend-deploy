package pl.edu.agh.productivitypal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailVerificationDto {
    String code;
    String email;
}
