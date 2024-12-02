package ru.kulbaka.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Форма аутентификации пользователя")
@Data
public class UserCredentialsDTO {

    @Schema(description = "Почта пользователя", example = "user@mail.ru")
    @NotBlank(message = "Email does not have to be blank")
    @Email(message = "Email must be valid")
    private String email;

    @Schema(description = "Пароль пользователя(Не меньше 4 символов)", example = "1111")
    @NotBlank(message = "Password does not have to be blank")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;
}
