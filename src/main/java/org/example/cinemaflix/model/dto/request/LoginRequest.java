package org.example.cinemaflix.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username cannot be empty or null")
    @Size(max = 50)
    @Pattern(regexp = "[A-Za-z0-9_.]+$")
    String username;

    @NotBlank(message = "Username cannot be empty or null")
    @Size(min=5)
    @Pattern(regexp = "[A-Za-z0-9_.]+$")
    String password;

}
