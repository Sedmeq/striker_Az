package org.example.striker_az.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class RegisterRequest {
    @NotBlank(message = "Ad və soyad tələb olunur")
    private String name;

    @Email(message = "Düzgün email daxil edin")
    @NotBlank(message = "Email tələb olunur")
    private String email;

    @NotBlank(message = "Şifrə tələb olunur")
    @Size(min = 6, message = "Şifrə ən azı 6 simvoldan ibarət olmalıdır")
    private String password;

    @NotBlank(message = "Şifrə təsdiqi tələb olunur")
    private String confirmPassword;
}