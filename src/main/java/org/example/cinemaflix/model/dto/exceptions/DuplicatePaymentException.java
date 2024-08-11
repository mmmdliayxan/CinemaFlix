package org.example.cinemaflix.model.dto.exceptions;

public class DuplicatePaymentException extends RuntimeException{

    public DuplicatePaymentException(String message){
        super(message);
    }
}
