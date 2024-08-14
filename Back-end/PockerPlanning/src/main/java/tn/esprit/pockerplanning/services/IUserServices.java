package tn.esprit.pockerplanning.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pockerplanning.entities.User;

import java.io.IOException;
import java.util.List;

public interface IUserServices {

    User getUserById(String id);
    User updateProfil(User user);

    User updatePassword (String id, String password);
    User updateImage(String id, MultipartFile file)throws IOException;
    List<User> findprojectmanager();

    List<User> finddeveloppeur();
    User activateUser(String userId);

    User deactivateUser(String userId);

    User activateAccount(String token);
}
