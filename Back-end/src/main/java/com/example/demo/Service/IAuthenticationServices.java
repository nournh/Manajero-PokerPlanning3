package com.example.demo.Service;


import com.example.demo.Entity.AuthenticationResponse;
import com.example.demo.Entity.RefreshTokenRequest;
import com.example.demo.Entity.User;
import jakarta.mail.MessagingException;

import java.util.HashMap;

public interface IAuthenticationServices {


    User register(User user);
    AuthenticationResponse login(String email, String password);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken);
    HashMap<String,String> forgetPassword(String email) throws MessagingException;
    HashMap<String,String> resetPassword(String passwordResetToken, String newPassword);
}
