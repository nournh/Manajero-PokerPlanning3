package com.example.demo.Controller;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pockerplanning.config.CloudinaryService;
import tn.esprit.pockerplanning.config.EmailService;
import com.example.demo.Entity.*;
import com.example.demo.Entity.enums.Complexity;
import com.example.demo.Entity.enums.Role;
import com.example.demo.Repositories.CardRepository;
import com.example.demo.Service.IAuthenticationServices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final IAuthenticationServices authenticationServices;
    private final EmailService emailService;
    private final CloudinaryService cloudinaryService;
private final CardRepository cardRepository;
    public static String uploadDirectory = System.getProperty("user.dir") + "/uploadUser";

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam("firstname") String firstname,
                                         @RequestParam("lastname") String lastname,
                                         @RequestParam("email") String email,
                                         @RequestParam("password") String password,
                                         @RequestParam("role") Role role,
                                         @RequestParam("picture") MultipartFile file) throws IOException, MessagingException {
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setVerified(false);
        user.setActive(true);

        // Upload the user's picture to Cloudinary
        Map uploadResult = cloudinaryService.upload(file);
        String pictureUrl = (String) uploadResult.get("url");
        user.setPicture(pictureUrl);

        // Générer un jeton d'activation unique
        String activationToken = UUID.randomUUID().toString();

        // Stocker le jeton d'activation dans l'utilisateur
        user.setActivationToken(activationToken);

        // Enregistrez l'utilisateur dans la base de données
        User savedUser = authenticationServices.register(user);

        // Créer le lien d'activation
        String activationLink = "http://localhost:8980/PockerPlanning/user/activate?token=" + activationToken;

        // Envoyez un e-mail de vérification avec le lien d'activation
        Mail mail = new Mail();
        mail.setSubject("Verification");
        mail.setTo(user.getEmail());
        mail.setContent("<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Activation de compte</title>"
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
                + ".activation-link {"
                + "    background-color: #007bff;"
                + "    color: #fff;"
                + "    text-decoration: none;"
                + "    padding: 10px 20px;"
                + "    border-radius: 5px;"
                + "    display: inline-block;"
                + "}"
                + ".activation-link:hover {"
                + "    background-color: #0056b3;"
                + "}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class=\"container\">"
                + "    <h2>Activation de compte</h2>"
                + "    <p>"
                + "        Bonjour <strong>" + user.getFirstname() + " " + user.getLastname() + "</strong>,"
                + "    </p>"
                + "    <p>"
                + "        Cliquez sur le bouton ci-dessous pour activer votre compte :"
                + "    </p>"
                + "    <p>"
                + "        <a href=\"" + activationLink + "\" class=\"activation-link\">Activer le compte</a>"
                + "    </p>"
                + "</div>"
                + "</body>"
                + "</html>");

        emailService.sendHtmlEmail(mail);

        // Créer les cartes pour l'utilisateur
        createCardsForUser(savedUser.getId());

        // Retournez la réponse HTTP avec l'utilisateur enregistré
        return ResponseEntity.ok(savedUser);
    }

    private void createCardsForUser(long userId) {
        Complexity[] complexities = Complexity.values();
        for (Complexity complexity : complexities) {
            Card card = new Card();
            card.setIdUser(userId);
            card.setComplexity(complexity);
            try {
                cardRepository.save(card);
                System.out.println("Card saved for user " + userId + " with complexity " + complexity);
            } catch (DataAccessException e) {
                System.err.println("Error saving card for user " + userId + " with complexity " + complexity + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws MalformedURLException {

        Path filePath = Paths.get(uploadDirectory).resolve(filename);
        Resource file = new UrlResource(filePath.toUri());

        if (file.exists() || file.isReadable()) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }


    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody User user) {
        return authenticationServices.login(user.getEmail(), user.getPassword());
    }

    @PostMapping("/refreshToken")
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        return authenticationServices.refreshToken(refreshToken);
    }

    @PostMapping("/forgetpassword")
    public HashMap<String,String> forgetPassword(@RequestParam String email) throws MessagingException {
        return authenticationServices.forgetPassword(email);
    }

    @PostMapping("/resetPassword/{passwordResetToken}")
    public HashMap<String,String> resetPassword(@PathVariable String passwordResetToken, String newPassword){
        return authenticationServices.resetPassword(passwordResetToken, newPassword);
    }

}
