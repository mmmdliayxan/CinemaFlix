package org.example.cinemaflix.controller;
import lombok.RequiredArgsConstructor;
import org.example.cinemaflix.model.dto.request.ChangePassword;
import org.example.cinemaflix.model.dto.request.LoginRequest;
import org.example.cinemaflix.model.dto.request.UserRequestDto;
import org.example.cinemaflix.service.AuthService;
import org.example.cinemaflix.service.ForgotPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ForgotPasswordService passwordService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserRequestDto userRequestDto){
        authService.register(userRequestDto);
    }

    @PostMapping("/send-email/{email}")
    public String sendEmail(@PathVariable String email){
        passwordService.sendEmail(email);
        return "email is sent for verification";
    }

    @PostMapping("/verify-token/{email}/{token}")
    public String verifyToken(@PathVariable String email,@PathVariable String token){
        passwordService.verifyToken(token,email);
        return "token verified successfully";
    }

    @PostMapping("/change-password/{email}")
    public String changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable String email){
        passwordService.changePasswordHandler(changePassword,email);
        return "Password successfully changed";
    }
}
