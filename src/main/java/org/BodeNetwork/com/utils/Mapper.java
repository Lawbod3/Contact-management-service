package org.BodeNetwork.com.utils;

import org.BodeNetwork.com.data.models.User;
import org.BodeNetwork.com.data.repositories.UserRepository;
import org.BodeNetwork.com.dtos.request.UserLoginRequest;
import org.BodeNetwork.com.dtos.request.UserRegistrationRequest;
import org.BodeNetwork.com.dtos.response.UserLoginResponse;
import org.BodeNetwork.com.dtos.response.UserRegistrationResponse;

public class Mapper {
    public static UserRegistrationResponse mapToRegistrationRequest( User user) {
        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setPhoneNumber(user.getPhoneNumber());
        response.setEmail(user.getEmail());
        response.setId(user.getId());
        return response;
    }
    public static UserLoginResponse mapToLoginRequest(User user) {
        UserLoginResponse response = new UserLoginResponse();
        response.setPhoneNumber(user.getPhoneNumber());
        response.setId(user.getId());
       return response;
    }
}
