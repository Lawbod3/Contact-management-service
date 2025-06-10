package org.BodeNetwork.com.service;

import org.BodeNetwork.com.data.repositories.ContactRepository;
import org.BodeNetwork.com.data.repositories.UserRepository;
import org.BodeNetwork.com.dtos.request.AddContactRequest;
import org.BodeNetwork.com.dtos.request.DeleteContactRequest;
import org.BodeNetwork.com.dtos.request.UserLoginRequest;
import org.BodeNetwork.com.dtos.request.UserRegistrationRequest;
import org.BodeNetwork.com.dtos.response.AddContactResponse;
import org.BodeNetwork.com.dtos.response.DeleteContactResponse;
import org.BodeNetwork.com.dtos.response.UserLoginResponse;
import org.BodeNetwork.com.dtos.response.UserRegistrationResponse;
import org.BodeNetwork.com.exceptions.PasswordException;
import org.BodeNetwork.com.exceptions.UserExistException;
import org.BodeNetwork.com.services.ContactManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;

@SpringBootTest
public class ContactManagementServiceTest {
    @Autowired
    ContactManagementService contactManagementService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ContactRepository contactRepository;
    private UserRegistrationRequest userRegistrationRequest;
    private UserRegistrationResponse userRegistrationResponse;
    private UserLoginRequest userLoginRequest;
    private UserLoginResponse userLoginResponse;
    private AddContactRequest addContactRequest;
    private AddContactResponse addContactResponse;
    private DeleteContactRequest deleteContactRequest;
    private DeleteContactResponse deleteContactResponse;



    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setEmail("test@test.com");
        userRegistrationRequest.setPassword("password");
        userRegistrationRequest.setPhoneNumber("1111111111");

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setPhoneNumber("1111111111");
        userLoginRequest.setPassword("password");

        addContactRequest = new AddContactRequest();
        addContactRequest.setEmail("test@test.com");
        addContactRequest.setPhoneNumber("1112222221");
        addContactRequest.setLastname("TestLastname");
        addContactRequest.setFirstname("TestFirstname");

        deleteContactRequest = new DeleteContactRequest();

    }
    @Test
    public void testThatContactServiceManagementCanRegisterUser() {
      userRegistrationResponse =  contactManagementService.registerUser(userRegistrationRequest);
      assertEquals(userRegistrationRequest.getEmail(), userRegistrationResponse.getEmail());
    }
    @Test
    public void testThatContactServiceManagementCanThrowExceptionForExistingUser() {
        userRegistrationResponse =  contactManagementService.registerUser(userRegistrationRequest);
        assertEquals(userRegistrationRequest.getEmail(), userRegistrationResponse.getEmail());
        assertThrows(UserExistException.class, () -> contactManagementService.registerUser(userRegistrationRequest));
    }

    @Test
    public void testThatContactServiceManagementCanGetUserWhenLoggedIn() {
        userRegistrationResponse =  contactManagementService.registerUser(userRegistrationRequest);
        assertEquals(userRegistrationRequest.getEmail(), userRegistrationResponse.getEmail());
        userLoginResponse = contactManagementService.loginUser(userLoginRequest);
        assertEquals(userRegistrationResponse.getId(), userLoginResponse.getId());
    }
    @Test
    public void testThatContactServiceManagementCanThrowExceptionForWrongPassword(){
        userRegistrationResponse =  contactManagementService.registerUser(userRegistrationRequest);
        assertEquals(userRegistrationRequest.getEmail(), userRegistrationResponse.getEmail());
        userLoginRequest.setPassword("wrongPassword");
        assertThrows(PasswordException.class, () -> contactManagementService.loginUser(userLoginRequest));

    }
    @Test
    public void testThatContactServiceManagementCanAddContactToUserContactList() {
        userRegistrationResponse =  contactManagementService.registerUser(userRegistrationRequest);
        assertEquals(userRegistrationRequest.getEmail(), userRegistrationResponse.getEmail());
        userLoginResponse = contactManagementService.loginUser(userLoginRequest);
        assertEquals(userRegistrationResponse.getId(), userLoginResponse.getId());
        addContactRequest.setUserId(userRegistrationResponse.getId());
        addContactResponse = contactManagementService.addContact(addContactRequest);
        assertEquals(addContactResponse.getUserId(), userLoginResponse.getId());

    }

    @Test
    public void testThatContactServiceManagementCanDeleteExistingContact() {
        userRegistrationResponse =  contactManagementService.registerUser(userRegistrationRequest);
        assertEquals(userRegistrationRequest.getEmail(), userRegistrationResponse.getEmail());
        userLoginResponse = contactManagementService.loginUser(userLoginRequest);
        assertEquals(userRegistrationResponse.getId(), userLoginResponse.getId());
        addContactRequest.setUserId(userRegistrationResponse.getId());
        addContactResponse = contactManagementService.addContact(addContactRequest);
        assertEquals(addContactResponse.getUserId(), userLoginResponse.getId());
        deleteContactRequest.setContactId(addContactResponse.getId());
        deleteContactResponse = contactManagementService.deleteContact(deleteContactRequest);
        assertFalse(contactRepository.existsById(deleteContactResponse.getId()));


    }




}
