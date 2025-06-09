package org.BodeNetwork.com.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class UserLoginRequest {
    @NotBlank(message = "PhoneNumber can not be blank")
    @Size(min = 3, max = 50, message = "PhoneNumber minimum size 3 characters , maximum 50 characters")
    private String phoneNumber;
    @NotBlank(message = ("Password can not be empty"))
    @Size(min = 6, max = 50, message = "Password have to be more than 6 characters ")
    private String password;
}
