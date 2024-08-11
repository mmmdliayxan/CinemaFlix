package org.example.cinemaflix.service;

import org.example.cinemaflix.model.dto.request.ChangePassword;

public interface ForgotPasswordService {

    void sendEmail(String email);

    void verifyToken(String token,String email);

    void changePasswordHandler(ChangePassword changePassword,String email);

}
