package org.BodeNetwork.com.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateContactRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    @NotBlank
    private String contactId;
}
