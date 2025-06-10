package org.BodeNetwork.com.services;

import org.BodeNetwork.com.data.models.Contact;
import org.BodeNetwork.com.data.models.User;
import org.BodeNetwork.com.data.repositories.ContactRepository;
import org.BodeNetwork.com.data.repositories.UserRepository;
import org.BodeNetwork.com.dtos.request.*;
import org.BodeNetwork.com.dtos.response.*;
import org.BodeNetwork.com.exceptions.ContactDoesNotExistException;
import org.BodeNetwork.com.exceptions.PasswordException;
import org.BodeNetwork.com.exceptions.UserDoesNotExistException;
import org.BodeNetwork.com.exceptions.UserExistException;
import org.BodeNetwork.com.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ContactManagementServiceImpl implements ContactManagementService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ContactRepository contactRepository;


    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        User user = new User();
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())) throw new UserExistException("User Already Exists");
        else {
            user.setPhoneNumber(request.getPhoneNumber());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            userRepository.save(user);
            return Mapper.mapToRegistrationResponse(user);
        }
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByPhoneNumber(userLoginRequest.getPhoneNumber())
                .orElseThrow(() ->  new UserDoesNotExistException("User not found"));
        if(!user.isValidPassword(userLoginRequest.getPassword())) throw new PasswordException("Invalid password");
        else{
            UserLoginResponse response= Mapper.mapToLoginResponse(user);
            response.setContacts(contactRepository.findByUserId(user.getId()));
            return response;
        }

    }

    @Override
    public AddContactResponse addContact(AddContactRequest addContactRequest) {
        User user = userRepository.findById(addContactRequest.getUserId())
                .orElseThrow(() -> new UserDoesNotExistException("User not found"));
        Contact contact = new Contact();
        contact.setUserId(user.getId());
        contact.setPhoneNumber(addContactRequest.getPhoneNumber());
        contact.setEmail(addContactRequest.getEmail());
        contact.setFirstname(addContactRequest.getFirstname());
        contact.setLastname(addContactRequest.getLastname());
        contactRepository.save(contact);
        return Mapper.mapToAddContactResponse(contact);
    }

    @Override
    public DeleteContactResponse deleteContact(DeleteContactRequest request) {
        try {
            Contact contact = contactRepository.findById(request.getContactId())
                    .orElseThrow(() -> new ContactDoesNotExistException("Contact does not exist"));
            contactRepository.delete(contact);
            return Mapper.mapToDeleteContactResponse(contact);
        }
        catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete contact due to database error", e);
        }
    }

    @Override
    public UpdateContactResponse updateContact(UpdateContactRequest request) {
        Contact contact = contactRepository.findById(request.getContactId())
                .orElseThrow(() -> new ContactDoesNotExistException("Contact does not exist"));
        contact.setFirstname(request.getFirstname());
        contact.setLastname(request.getLastname());
        contact.setEmail(request.getEmail());
        contact.setPhoneNumber(request.getPhoneNumber());
        contactRepository.save(contact);
        return Mapper.mapToUpdateContactResponse(contact);
    }


}
