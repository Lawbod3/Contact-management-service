package org.BodeNetwork.com.data.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("User")
public class User {
    @Id
    private String id;
    private String phoneNumber;
    @Getter(AccessLevel.NONE)
    private String password;
    private boolean isValidPassword;
}
