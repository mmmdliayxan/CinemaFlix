package org.example.cinemaflix.model.dto.exceptions;

public class SeatCountMismatchException extends RuntimeException{

    public SeatCountMismatchException(String message){
        super(message);
    }
}
