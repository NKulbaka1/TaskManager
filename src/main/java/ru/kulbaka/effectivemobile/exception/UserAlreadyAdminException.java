package ru.kulbaka.effectivemobile.exception;

public class UserAlreadyAdminException extends RuntimeException {

    public UserAlreadyAdminException () {
    }

    public UserAlreadyAdminException(String message) {
        super(message);
    }
}
