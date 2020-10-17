package ru.pvolan.archsample.logic.exception;

//Base class for all exceptions which can be thrown from this module
public class LogicException extends Exception {

    public LogicException() {
    }

    public LogicException(String message) {
        super(message);
    }

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicException(Throwable cause) {
        super(cause);
    }
}
