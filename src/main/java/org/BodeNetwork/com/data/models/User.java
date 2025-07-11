package org.BodeNetwork.com.data.models;


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
    private String email;
    @Getter(AccessLevel.NONE)
    private String password;


    public boolean isValidPassword( String password) {
        return this.password.equals(password);
    }
}
