package org.BodeNetwork.com.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeleteContactRequest {

    @NotBlank(message = "PhoneNumber can not be empty")
    private String contactId;
}
