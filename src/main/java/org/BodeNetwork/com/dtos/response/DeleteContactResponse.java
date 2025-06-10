package org.BodeNetwork.com.dtos.response;

import lombok.Data;

@Data
public class DeleteContactResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String userId;
}
