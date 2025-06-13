package org.BodeNetwork.com.dtos.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DeleteContactRequest {

    @NotBlank
    private String contactId;
}
