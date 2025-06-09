package org.BodeNetwork.com.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddContactRequest {
    private String firstname;
    private String lastname;
    private String email;
    @NotBlank(message = "PhoneNumber can not be empty")
    @Size(min = 1, max = 50, message = "PhoneNumber must have at least 1 character and maximum of 50 characters ")
    private String phoneNumber;
}
