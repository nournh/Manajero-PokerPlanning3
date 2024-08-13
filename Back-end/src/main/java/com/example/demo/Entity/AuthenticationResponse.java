package  com.example.demo.Entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "authenticationResponses") // This specifies the MongoDB collection name
public class AuthenticationResponse {

    @Id
    long id; // This is the unique identifier for MongoDB documents

    String accessToken;
    String refreshToken;
   User userDetails;
}

