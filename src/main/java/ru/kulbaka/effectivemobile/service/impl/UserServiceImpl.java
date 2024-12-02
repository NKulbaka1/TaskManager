package ru.kulbaka.effectivemobile.service.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kulbaka.effectivemobile.entity.User;
import ru.kulbaka.effectivemobile.exception.EmailExistsException;
import ru.kulbaka.effectivemobile.exception.UserAlreadyAdminException;
import ru.kulbaka.effectivemobile.exception.UserNotFoundException;
import ru.kulbaka.effectivemobile.model.UserRole;
import ru.kulbaka.effectivemobile.repository.UserRepository;
import ru.kulbaka.effectivemobile.security.UserDetailsImpl;
import ru.kulbaka.effectivemobile.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailExistsException("User already exists");
        }

        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User " + email + " not found"));

    }

    @Override
    public UserDetailsService userDetailsService() {
        return email -> {
            User user = getByEmail(email);
            return UserDetailsImpl.build(user);
        };
    }

    @Override
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(email);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public String getAdmin() {
        User user = getCurrentUser();
        if (user.getUserRole() == UserRole.ROLE_ADMIN) {
            throw new UserAlreadyAdminException("User already admin");
        }
        user.setUserRole(UserRole.ROLE_ADMIN);
        userRepository.save(user);
        return "user " + user.getEmail() + " has been assigned the admin role";
    }
}
