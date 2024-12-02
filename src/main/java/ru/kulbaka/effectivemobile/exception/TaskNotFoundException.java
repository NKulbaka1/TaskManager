package ru.kulbaka.effectivemobile.exception;

/**
 * @author Кульбака Никита
 * Исключение выбрасывается если задача не была найдена
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException () {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
