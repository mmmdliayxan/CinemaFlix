package org.example.cinemaflix.model.dto.exceptions;

public class UserIsNotActiveException extends RuntimeException{

    public UserIsNotActiveException(String message){
        super(message);
    }
}
