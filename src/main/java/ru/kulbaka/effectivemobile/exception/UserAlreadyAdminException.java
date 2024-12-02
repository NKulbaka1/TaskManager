package ru.kulbaka.effectivemobile.exception;

/**
 * @author Кульбака Никита
 * Исключение выбрасывается если пользователь уже имеет роль администратора
 */
public class UserAlreadyAdminException extends RuntimeException {

    public UserAlreadyAdminException () {
    }

    public UserAlreadyAdminException(String message) {
        super(message);
    }
}
