package org.example.cinemaflix.model.dto.request;

import lombok.Data;

@Data
public class ChangePassword {
    private String password;
    private String repeatPassword;
}
