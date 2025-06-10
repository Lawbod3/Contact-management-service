package org.BodeNetwork.com.service;

import org.BodeNetwork.com.data.repositories.UserRepository;
import org.BodeNetwork.com.dtos.request.UserRegistrationRequest;
import org.BodeNetwork.com.dtos.response.UserRegistrationResponse;
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
    private UserRegistrationRequest userRegistrationRequest;
    private UserRegistrationResponse userRegistrationResponse;


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setEmail("test@test.com");
        userRegistrationRequest.setPassword("password");
        userRegistrationRequest.setPhoneNumber("1111111111");

    }

    @Test
    public void testThatContactServiceManagementCanRegisterUser() {
      userRegistrationResponse =  contactManagementService.registerUser(userRegistrationRequest);
      assertEquals(userRegistrationRequest.getEmail(), userRegistrationResponse.getEmail());
    }


}
