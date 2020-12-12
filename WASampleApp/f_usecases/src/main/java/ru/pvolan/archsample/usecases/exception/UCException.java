package ru.pvolan.archsample.usecases.exception;

//Base class for all exceptions which can be thrown from this module
public class UCException extends Exception {

    public UCException() {
    }

    public UCException(String message) {
        super(message);
    }

    public UCException(String message, Throwable cause) {
        super(message, cause);
    }

    public UCException(Throwable cause) {
        super(cause);
    }
}
