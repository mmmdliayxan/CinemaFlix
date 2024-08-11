package org.example.cinemaflix.model.dto.exceptions;

public class TimeExpiredException extends RuntimeException{

    public TimeExpiredException(String message){
        super(message);
    }
}
