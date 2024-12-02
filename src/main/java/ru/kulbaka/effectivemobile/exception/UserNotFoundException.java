package ru.kulbaka.effectivemobile.exception;

/**
 * @author Кульбака Никита
 * Исключение выбрасывается если пользователь не был найден
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
