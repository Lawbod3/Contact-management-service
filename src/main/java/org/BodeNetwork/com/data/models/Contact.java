package org.BodeNetwork.com.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document("Contact")
public class Contact {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    @Field("userId")
    private String userId;
}