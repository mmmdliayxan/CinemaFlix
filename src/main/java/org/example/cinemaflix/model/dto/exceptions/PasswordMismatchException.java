package org.example.cinemaflix.model.dto.exceptions;

public class PasswordMismatchException extends RuntimeException{

    public PasswordMismatchException(String message){
        super(message);
    }
}
