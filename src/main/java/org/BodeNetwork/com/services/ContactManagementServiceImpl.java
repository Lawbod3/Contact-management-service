package org.BodeNetwork.com.services;

import org.BodeNetwork.com.data.models.User;
import org.BodeNetwork.com.data.repositories.ContactRepository;
import org.BodeNetwork.com.data.repositories.UserRepository;
import org.BodeNetwork.com.dtos.request.*;
import org.BodeNetwork.com.dtos.response.*;
import org.BodeNetwork.com.exceptions.UserExistException;
import org.BodeNetwork.com.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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
            return Mapper.mapToRegistrationRequest(user);
        }
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
