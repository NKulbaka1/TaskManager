package ru.kulbaka.effectivemobile.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kulbaka.effectivemobile.entity.User;

public interface UserService {

    User create(User user);

    User getByEmail(String email);

    UserDetailsService userDetailsService();

    User getCurrentUser();

    boolean emailExists(String email);

    void getAdmin();
}
