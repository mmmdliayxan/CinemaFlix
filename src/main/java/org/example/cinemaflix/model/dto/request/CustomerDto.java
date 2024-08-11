package org.example.cinemaflix.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerDto {

    @NotNull
    public String name;

    @NotNull
    public String surname;

    @Email
    @NotBlank(message = "email cannot be empty or null")
    @Size(max = 40)
    private String email;

    @NotBlank(message = "phone can not be empty or null")
    @Pattern(regexp = "^[0-9]+$")
    @Size(min = 10, max = 12)
    private String phoneNumber;
}
