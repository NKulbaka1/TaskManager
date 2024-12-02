package ru.kulbaka.effectivemobile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kulbaka.effectivemobile.dto.UserCredentialsDTO;
import ru.kulbaka.effectivemobile.dto.TokenDTO;
import ru.kulbaka.effectivemobile.entity.User;
import ru.kulbaka.effectivemobile.model.UserRole;
import ru.kulbaka.effectivemobile.security.JwtService;
import ru.kulbaka.effectivemobile.security.UserDetailsImpl;
import ru.kulbaka.effectivemobile.service.AuthenticationService;
import ru.kulbaka.effectivemobile.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    public TokenDTO signUp(UserCredentialsDTO userCredentialsDTO) {
        User user = new User(userCredentialsDTO.getEmail(), passwordEncoder.encode(userCredentialsDTO.getPassword()), UserRole.ROLE_USER);

        userService.create(user);

        return new TokenDTO(jwtService.generateToken(UserDetailsImpl.build(user)));
    }

    @Override
    public TokenDTO signIn(UserCredentialsDTO signInDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDTO.getEmail(),
                signInDTO.getPassword()
        ));

        User user = userService.getByEmail(signInDTO.getEmail());

        return new TokenDTO(jwtService.generateToken(UserDetailsImpl.build(user)));
    }
}
