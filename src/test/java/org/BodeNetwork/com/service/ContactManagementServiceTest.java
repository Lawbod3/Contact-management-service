package org.BodeNetwork.com.service;

import org.BodeNetwork.com.data.repositories.ContactRepository;
import org.BodeNetwork.com.data.repositories.UserRepository;
import org.BodeNetwork.com.dtos.request.UserLoginRequest;
import org.BodeNetwork.com.dtos.request.UserRegistrationRequest;
import org.BodeNetwork.com.dtos.response.UserLoginResponse;
import org.BodeNetwork.com.dtos.response.UserRegistrationResponse;
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
    private



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




}
