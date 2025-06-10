package org.BodeNetwork.com.services;


import org.BodeNetwork.com.dtos.request.*;
import org.BodeNetwork.com.dtos.response.*;

public interface ContactManagementService {
    UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest);
    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);
    AddContactResponse addContact(AddContactRequest addContactRequest);
    DeleteContactResponse deleteContact(DeleteContactRequest deleteContactRequest);
    UpdateContactResponse updateContact(UpdateContactRequest updateContactRequest);
}
