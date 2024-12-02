package ru.kulbaka.effectivemobile.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kulbaka.effectivemobile.security.UserDetailsImpl;

public interface JwtService {

    String extractEmail(String token);

    String generateToken(UserDetailsImpl userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
