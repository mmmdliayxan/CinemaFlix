package org.example.cinemaflix.model.dto.response;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.cinemaflix.dao.entity.Authority;
import org.example.cinemaflix.model.dto.enums.UserStatus;

import java.util.Set;

@Data
public class UserResponse {

    private String firstName;
    private String lastName;

    private String email;
    private String phoneNumber;

    private String username;
    private Set<Authority> authorities;

    @Size(min = 8)
    private String password;

    private UserStatus userStatus;

}
