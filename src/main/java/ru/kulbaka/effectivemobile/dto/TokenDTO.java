package ru.kulbaka.effectivemobile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "DTO для отправки JWT токена клиенту")
@Data
@AllArgsConstructor
public class TokenDTO {

    @Schema(description = "JWT токен")
    private String token;

}
