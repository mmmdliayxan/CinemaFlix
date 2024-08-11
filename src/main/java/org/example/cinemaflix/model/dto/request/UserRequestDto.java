package org.example.cinemaflix.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "model for user registration")
public class UserRequestDto {

   private CustomerDto customerDto;

   @Schema(description = "username",example = "")
   private String username;

   @Size(min = 8)
   private String password;
   
   private String role;
}
