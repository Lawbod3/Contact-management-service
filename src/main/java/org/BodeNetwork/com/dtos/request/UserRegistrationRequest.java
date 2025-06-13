package org.BodeNetwork.com.dtos.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @NotBlank
    @Size(min = 3, max = 50, message = "PhoneNumber minimum size 3 characters , maximum 50 characters")
    private String phoneNumber;
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 50, message = "Password have to be more than 6 characters ")
    private String password;
}
