package me.kiryakov.animal_chips.exception;

public class InaccessibleEntityException extends RuntimeException {
    public InaccessibleEntityException(String message) {
        super(message);
    }

    public InaccessibleEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
