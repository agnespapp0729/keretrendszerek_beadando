package hu.uni.eku.tzs.service.exceptions;

import hu.uni.eku.tzs.model.Actor;

public class ActorAlreadyExitsException extends Exception{
    public ActorAlreadyExitsException(){

    }

    public ActorAlreadyExitsException(String message){
        super(message);
    }

    public ActorAlreadyExitsException(String message, Throwable cause){
        super(message, cause);
    }

    public ActorAlreadyExitsException(Throwable cause){
        super(cause);
    }

    public ActorAlreadyExitsException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
