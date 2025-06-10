package org.BodeNetwork.com.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FindContactRequest {
    private String contactName;
    @Email(message = "Invalid email format")
    private String contactEmail;
    @NotBlank(message = "Phone number can not be blank")
    private String phoneNumber;
}
