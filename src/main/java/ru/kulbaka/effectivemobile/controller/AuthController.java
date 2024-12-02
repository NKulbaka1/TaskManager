package ru.kulbaka.effectivemobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kulbaka.effectivemobile.dto.UserCredentialsDTO;
import ru.kulbaka.effectivemobile.dto.TokenDTO;
import ru.kulbaka.effectivemobile.service.AuthenticationService;
import ru.kulbaka.effectivemobile.service.UserService;

@Tag(name = "Authentication Controller", description = "Контроллер для аутентификации и управления правами")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    private final UserService userService;

    @Operation(
            summary = "Регистрация пользователя",
            description = """
                    Позволяет зарегистрировать пользователя
                    Доступ: любой пользователь
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешная регистрация", content = @Content(schema = @Schema(implementation = TokenDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Ошибка ввода", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @PostMapping("/sign-up")
    public ResponseEntity<TokenDTO> signUp(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signUp(userCredentialsDTO));
    }

    @Operation(
            summary = "Аутентификация пользователя",
            description = """
                    Позволяет аутентифицировать пользователя
                    Доступ: любой пользователь
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешная аутентификация", content = @Content(schema = @Schema(implementation = TokenDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Ошибка ввода", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @PostMapping("/sign-in")
    public ResponseEntity<TokenDTO> signIn(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signIn(userCredentialsDTO));
    }

    @Operation(
            summary = "Получение роли администратора",
            description = """
                    Открывает доступ к расширенному взаимодействию с задачами с правами администратора
                    Доступ: Любой пользователь
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Роль выдана", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Роль уже выдана ранее", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "401", description = "Ошибка аутентификации", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Ошибка на стороне сервера")
            }
    )
    @SecurityRequirement(name = "JWT")
    @PatchMapping("/get-admin")
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAdmin());
    }

}
