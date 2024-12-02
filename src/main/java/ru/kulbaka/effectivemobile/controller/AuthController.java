package ru.kulbaka.effectivemobile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
            description = "Позволяет зарегистрировать пользователя"
    )
    @PostMapping("/sign-up")
    public TokenDTO signUp(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) {
        return authenticationService.signUp(userCredentialsDTO);
    }

    @Operation(
            summary = "Аутентификация пользователя",
            description = "Позволяет аутентифицировать пользователя"
    )
    @PostMapping("/sign-in")
    public TokenDTO signIn(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) {
        return authenticationService.signIn(userCredentialsDTO);
    }

    @Operation(
            summary = "Получение роли администратора",
            description = """
                    Открывает доступ к расширенному взаимодействию с задачами с правами администратора
                    Доступ: USER
                    """
    )
    @PatchMapping("/get-admin")
    public void getAdmin() {
        userService.getAdmin();
    }

}
