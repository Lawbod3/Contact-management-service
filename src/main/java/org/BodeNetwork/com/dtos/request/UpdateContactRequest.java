package org.BodeNetwork.com.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateContactRequest {
    private String firstname;
    private String lastname;
    private String email;
    @NotBlank(message = "PhoneNumber can not be empty")
    private String phoneNumber;
}
