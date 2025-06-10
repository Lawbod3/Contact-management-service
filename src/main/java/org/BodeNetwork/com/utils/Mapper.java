package org.BodeNetwork.com.utils;

import org.BodeNetwork.com.data.models.Contact;
import org.BodeNetwork.com.data.models.User;
import org.BodeNetwork.com.dtos.response.*;

public class Mapper {
    public static UserRegistrationResponse mapToRegistrationResponse(User user) {
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setPhoneNumber(user.getPhoneNumber());
        response.setEmail(user.getEmail());
        response.setId(user.getId());
        return response;
    }
    public static UserLoginResponse mapToLoginResponse(User user) {
        UserLoginResponse response = new UserLoginResponse();
        response.setPhoneNumber(user.getPhoneNumber());
        response.setId(user.getId());
       return response;
    }

    public static AddContactResponse mapToAddContactResponse(Contact contact) {
        AddContactResponse response = new AddContactResponse();
        response.setEmail(contact.getEmail());
        response.setPhoneNumber(contact.getPhoneNumber());
        response.setId(contact.getId());
        response.setFirstname(contact.getFirstname());
        response.setLastname(contact.getLastname());
        response.setUserId(contact.getUserId());
        return response;
    }
    public static DeleteContactResponse mapToDeleteContactResponse(Contact contact) {
        DeleteContactResponse response = new DeleteContactResponse();
        response.setEmail(contact.getEmail());
        response.setPhoneNumber(contact.getPhoneNumber());
        response.setId(contact.getId());
        response.setFirstname(contact.getFirstname());
        response.setLastname(contact.getLastname());
        response.setUserId(contact.getUserId());
        return response;
    }

    public static UpdateContactResponse mapToUpdateContactResponse(Contact contact) {
        UpdateContactResponse response = new UpdateContactResponse();
        response.setEmail(contact.getEmail());
        response.setPhoneNumber(contact.getPhoneNumber());
        response.setFirstname(contact.getFirstname());
        response.setLastname(contact.getLastname());
        return response;
    }
}
