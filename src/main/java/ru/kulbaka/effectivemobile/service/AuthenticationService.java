package ru.kulbaka.effectivemobile.service;

import ru.kulbaka.effectivemobile.dto.UserCredentialsDTO;
import ru.kulbaka.effectivemobile.dto.TokenDTO;

public interface AuthenticationService {

    TokenDTO signUp(UserCredentialsDTO userCredentialsDTO);

    TokenDTO signIn(UserCredentialsDTO userCredentialsDTO);
}
