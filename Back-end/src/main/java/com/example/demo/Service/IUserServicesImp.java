package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.config.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class IUserServicesImp implements IUserServices {

    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    private final PasswordEncoder passwordEncoder;



    @Override
    public User getUserById(Long id) {
        return userRepository.findById(String.valueOf(id)).orElse(null);
    }

    @Override
    public User updateProfil(User user) {
        User existinguser = userRepository.findById(String.valueOf(Long.valueOf(user.getId()))).orElse(null);
        if (existinguser != null) {
            if (user.getFirstname() != null) {
                existinguser.setFirstname(user.getFirstname());
            }
            if (user.getLastname() != null) {
                existinguser.setLastname(user.getLastname());
            }
            if (user.getPicture() != null) {
                existinguser.setPicture(user.getPicture());
            }
            if (user.getRole() != null) {
                existinguser.setRole(user.getRole());
            }
            if (user.getEmail() != null) {
                existinguser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                existinguser.setPassword(user.getPassword());
            }
        }
        return userRepository.save(existinguser);
    }

    @Override
    public User updatePassword(Long id, String password) {
        User user =userRepository.findById(String.valueOf((id))).orElse(null);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public User updateImage(Long id, MultipartFile file) {
        try {
            User user = userRepository.findById(String.valueOf((id))).orElse(null);

            // Upload the image file to Cloudinary
            Map uploadResult = cloudinaryService.upload(file);
            String imageUrl = (String) uploadResult.get("url");

            // Set the Cloudinary image URL to the user's picture field
            user.setPicture(imageUrl);

            // Save the updated user with Cloudinary image URL
            return userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Error processing file", e);
        }
    }


    @Override
    public List<User> findprojectmanager() {
        return userRepository.findprojectmanager();
    }

    @Override
    public List<User> finddeveloppeur() {
        return userRepository.finddeveloppeur();
    }

    @Override
    public User activateUser(Long userId) {
      User user  = userRepository.findById(String.valueOf((userId))).orElse(null);
        if (user !=null) {
            user.setActive(true); // Activer l'utilisateur
            return userRepository.save(user);
        } else {
            return null; // Utilisateur non trouvé
        }
    }
    @Override
    public User deactivateUser(Long userId) {
        User user = userRepository.findById(String.valueOf((userId))).orElse(null);
        if (user != null) {
            user.setActive(false);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User activateAccount(String token) {
        // Trouver l'utilisateur correspondant au jeton
        User user = userRepository.findByActivationToken(token);
        if (user != null) {
            // Modifier l'attribut verified de l'utilisateur de false à true
            user.setVerified(true);
            // Enregistrer les modifications dans la base de données
            userRepository.save(user);
        }
        return user;
    }


}
