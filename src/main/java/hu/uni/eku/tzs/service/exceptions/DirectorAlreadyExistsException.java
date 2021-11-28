package hu.uni.eku.tzs.service.exceptions;

public class DirectorAlreadyExistsException extends Exception{
    public DirectorAlreadyExistsException(){

    }

    public DirectorAlreadyExistsException(String message){
        super(message);
    }

    public DirectorAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }

    public DirectorAlreadyExistsException(Throwable cause){
        super(cause);
    }

    public DirectorAlreadyExistsException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
