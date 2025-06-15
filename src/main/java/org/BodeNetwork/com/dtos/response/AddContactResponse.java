package org.BodeNetwork.com.dtos.response;

import lombok.Data;
import org.BodeNetwork.com.data.models.Contact;

import java.util.List;


@Data
public class AddContactResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String userId;
    private List<Contact> contacts;
}
