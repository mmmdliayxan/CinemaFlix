package org.example.cinemaflix.model.dto.exceptions;

public class UnAvailableException extends RuntimeException{

    public UnAvailableException(String msg){
        super(msg);
    }


}
