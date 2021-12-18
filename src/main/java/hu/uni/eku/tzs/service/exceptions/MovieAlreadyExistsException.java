package hu.uni.eku.tzs.service.exceptions;

public class MovieAlreadyExistsException extends Exception {
    public MovieAlreadyExistsException() {

    }

    public MovieAlreadyExistsException(String message) {
        super(message);
    }

    public MovieAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public MovieAlreadyExistsException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
