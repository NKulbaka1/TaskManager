package ru.kulbaka.effectivemobile.exception;

/**
 * @author Кульбака Никита
 * Исключение выбрасывается если комментарий не был найден
 */
public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException () {
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
