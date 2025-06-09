package org.BodeNetwork.com.services;

import org.BodeNetwork.com.data.repositories.ContactRepository;
import org.BodeNetwork.com.data.repositories.UserRepository;
import org.BodeNetwork.com.dtos.request.*;
import org.BodeNetwork.com.dtos.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactManagementServiceImpl implements ContactManagementService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ContactRepository contactRepository;


    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) {
        return null;
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
        return null;
    }

    @Override
    public AddContactResponse addContact(AddContactRequest addContactRequest) {
        return null;
    }

    @Override
    public DeleteContactResponse deleteContact(DeleteContactRequest deleteContactRequest) {
        return null;
    }

    @Override
    public FindContactResponse findContact(FindContactRequest findContactRequest) {
        return null;
    }

    @Override
    public UpdateContactResponse updateContact(UserRegistrationRequest userRegistrationRequest) {
        return null;
    }
}
