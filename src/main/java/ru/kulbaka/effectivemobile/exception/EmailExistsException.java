package ru.kulbaka.effectivemobile.exception;

/**
 * @author Кульбака Никита
 * Исключение выбрасывается если пользователь с такой почтой уже существует
 */
public class EmailExistsException extends RuntimeException {
    public EmailExistsException () {
    }

    public EmailExistsException(String message) {
        super(message);
    }
}
