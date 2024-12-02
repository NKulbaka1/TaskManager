package ru.kulbaka.effectivemobile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCredentialsDTO {

    @NotBlank(message = "Email does not have to be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password does not have to be blank")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;
}
