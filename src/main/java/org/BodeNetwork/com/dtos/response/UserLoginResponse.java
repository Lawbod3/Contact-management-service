package org.BodeNetwork.com.dtos.response;

import lombok.Data;
import org.BodeNetwork.com.data.models.Contact;

import java.util.List;

@Data
public class UserLoginResponse {

    private String id;
    private String phoneNumber;
    private List<Contact> contacts;
}
