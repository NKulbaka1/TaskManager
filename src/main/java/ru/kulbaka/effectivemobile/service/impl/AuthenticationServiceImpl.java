package ru.kulbaka.effectivemobile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kulbaka.effectivemobile.dto.UserCredentialsDTO;
import ru.kulbaka.effectivemobile.dto.TokenDTO;
import ru.kulbaka.effectivemobile.entity.User;
import ru.kulbaka.effectivemobile.exception.CommentNotFoundException;
import ru.kulbaka.effectivemobile.model.UserRole;
import ru.kulbaka.effectivemobile.security.JwtServiceImpl;
import ru.kulbaka.effectivemobile.security.UserDetailsImpl;
import ru.kulbaka.effectivemobile.service.AuthenticationService;
import ru.kulbaka.effectivemobile.service.UserService;

/**
 * @author Кульбака Никита
 * Сервис для работы с аутентификацией
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final JwtServiceImpl jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    /**
     * Регистрирует пользователя в сервисе
     *
     * @param userCredentialsDTO почта и пароль
     * @return токен доступа
     */
    @Override
    @Transactional
    public TokenDTO signUp(UserCredentialsDTO userCredentialsDTO) {
        User user = new User(userCredentialsDTO.getEmail(), passwordEncoder.encode(userCredentialsDTO.getPassword()), UserRole.ROLE_USER);

        userService.create(user);

        return new TokenDTO(jwtService.generateToken(UserDetailsImpl.build(user)));
    }

    /**
     * Аутентифицирует зарегистрированного пользователя в сервисе
     *
     * @param signInDTO почта и пароль
     * @return токен доступа
     */
    @Override
    @Transactional(readOnly = true)
    public TokenDTO signIn(UserCredentialsDTO signInDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDTO.getEmail(),
                signInDTO.getPassword()
        ));

        User user = userService.getByEmail(signInDTO.getEmail());

        return new TokenDTO(jwtService.generateToken(UserDetailsImpl.build(user)));
    }
}
