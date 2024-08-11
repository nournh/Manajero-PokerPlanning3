package com.example.demo.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import tn.esprit.pockerplanning.config.EmailService;
import com.example.demo.Entity.AuthenticationResponse;
import com.example.demo.Entity.Mail;
import com.example.demo.Entity.RefreshTokenRequest;
import com.example.demo.Entity.User;
import com.example.demo.Repositories.UserRepository;

import java.util.HashMap;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class IAuthenticationServicesImp implements IAuthenticationServices{
    private final UserRepository  userRepository;

    private final AuthenticationManager  authenticationManager;
    private final IJWTServices jwtServices;
    private final  PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AuthenticationResponse login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var user = userRepository.findByEmail(email).orElse(null);

        // Vérifier si l'utilisateur est vérifié
        if (user.getVerified() && user.getActive()) {
            var jwt = jwtServices.generateToken(user);
            var refreshToken = jwtServices.generateRefreshToken(new HashMap<>(), user);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setAccessToken(jwt);
            authenticationResponse.setRefreshToken(refreshToken);

            User userDetails = convertToUserDto(user);
            authenticationResponse.setUserDetails(userDetails);

            return authenticationResponse;
        } else {
            return null;
        }
    }

    private User convertToUserDto(User user) {
        User dto = new User();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setPicture(user.getPicture());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        dto.setVerified(user.getVerified());
        return dto;
    }



    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken) {
        String userEmail = jwtServices.extractUsername(refreshToken.getRefreshToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        if(jwtServices.isTokenValid(refreshToken.getRefreshToken(), user)) {
            var jwt = jwtServices.generateToken(user);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();

            authenticationResponse.setAccessToken(jwt);
            authenticationResponse.setRefreshToken(refreshToken.getRefreshToken());
            return authenticationResponse;
        }
        return null;
    }

    @Override
    public HashMap<String, String> forgetPassword(String email) throws MessagingException {

        HashMap message = new HashMap();

        User userexisting = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        UUID token = UUID.randomUUID();
        userexisting.setPasswordResetToken(token.toString());
        userexisting.setId(userexisting.getId());

        String resetpasswordLink = "http://localhost:4200/resetpassword/"+userexisting.getPasswordResetToken();

        Mail mail = new Mail();
        mail.setSubject("Reset Password");
        mail.setTo(userexisting.getEmail());
        mail.setContent("<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Réinitialisation de mot de passe</title>"
                + "<style>"
                + "body {"
                + "    font-family: Arial, sans-serif;"
                + "    background-color: #f5f5f5;"
                + "    margin: 0;"
                + "    padding: 0;"
                + "    display: flex;"
                + "    justify-content: center;"
                + "    align-items: center;"
                + "    height: 100vh;"
                + "}"
                + ".container {"
                + "    background-color: #fff;"
                + "    padding: 20px;"
                + "    border-radius: 5px;"
                + "    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "    text-align: center;"
                + "}"
                + ".reset-link {"
                + "    background-color: #007bff;"
                + "    color: #fff;"
                + "    text-decoration: none;"
                + "    padding: 10px 20px;"
                + "    border-radius: 5px;"
                + "    display: inline-block;"
                + "}"
                + ".reset-link:hover {"
                + "    background-color: #0056b3;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class=\"container\">"
                + "    <h2>Réinitialisation de mot de passe</h2>"
                + "    <p>"
                + "        Bonjour <strong>" + userexisting.getFirstname() + " " + userexisting.getLastname() + "</strong>,"
                + "    </p>"
                + "    <p>"
                + "        Cliquez sur le lien ci-dessous pour réinitialiser votre mot de passe :"
                + "    </p>"
                + "    <p>"
                + "        <a href=\"" + resetpasswordLink + "\" class=\"reset-link\">" + "Changer mot de passe" + "</a>"
                + "    </p>"
                + "</div>"
                + "</body>"
                + "</html>");

        emailService.sendHtmlEmail(mail);

        userRepository.save(userexisting);
        message.put("user","user FOUND and email is Sent");
        return message;
    }

    @Override
    public HashMap<String, String> resetPassword(String passwordResetToken, String newPassword) {
        User userexisting = userRepository.findByPasswordResetToken(passwordResetToken).orElseThrow(() -> new RuntimeException("User not found"));
        HashMap message = new HashMap();
        if (userexisting != null) {
            userexisting.setId(userexisting.getId());
            userexisting.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userexisting.setPasswordResetToken(null);
            userRepository.save(userexisting);
            message.put("resetpassword","succès");
            return message;
        }else
        {
            message.put("resetpassword","Échoué ");
            return message;
        }
    }
}
