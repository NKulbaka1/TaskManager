package ru.kulbaka.effectivemobile.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kulbaka.effectivemobile.entity.User;
import ru.kulbaka.effectivemobile.exception.EmailExistsException;
import ru.kulbaka.effectivemobile.exception.UserAlreadyAdminException;
import ru.kulbaka.effectivemobile.exception.UserNotFoundException;
import ru.kulbaka.effectivemobile.model.UserRole;
import ru.kulbaka.effectivemobile.repository.UserRepository;
import ru.kulbaka.effectivemobile.security.UserDetailsImpl;
import ru.kulbaka.effectivemobile.service.UserService;

/**
 * @author Кульбака Никита
 * Сервис для работы с пользователями
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Сохраненяет пользователя
     *
     * @param user пользователь
     * @return сохраненный пользователь
     * @throws EmailExistsException если такой пользователь уже существует
     */
    @Override
    @Transactional
    public User create(User user) throws EmailExistsException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailExistsException("User already exists");
        }

        return userRepository.save(user);
    }

    /**
     * Получает пользователя по его почте
     *
     * @param email почта пользователя
     * @return найденный пользователь
     * @throws UserNotFoundException если пользователь не найден
     */
    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) throws UserNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User " + email + " not found"));

    }

    /**
     * Получает пользователя по его почте. Нужен для Spring Security
     *
     * @return найденный пользователь
     * @throws UserNotFoundException если пользователь не найден
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetailsService userDetailsService() throws UserNotFoundException {
        return email -> {
            User user = getByEmail(email);
            return UserDetailsImpl.build(user);
        };
    }

    /**
     * Получает текущего пользователя из контекста Spring Security
     *
     * @return найденный пользователь
     * @throws UserNotFoundException если пользователь не найден
     */
    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() throws UserNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(email);
    }

    /**
     * Выдаёт текущему пользователю роль администратора
     *
     * @return найденный пользователь
     * @throws UserAlreadyAdminException если пользователь уже админ
     * @throws UserNotFoundException     если пользователь не найден
     */
    @Override
    @Transactional
    public String getAdmin() throws UserNotFoundException, UserAlreadyAdminException {
        User user = getCurrentUser();
        if (user.getUserRole() == UserRole.ROLE_ADMIN) {
            throw new UserAlreadyAdminException("User already admin");
        }
        user.setUserRole(UserRole.ROLE_ADMIN);
        userRepository.save(user);
        return "user " + user.getEmail() + " has been assigned the admin role";
    }
}
