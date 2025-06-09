package org.BodeNetwork.com.controllers;

import org.BodeNetwork.com.data.repositories.UserRepository;
import org.BodeNetwork.com.services.ContactManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/BodeNetwork-user")
public class UserControllers {
    @Autowired
    ContactManagementService contactManagementService;

    @GetMapping("/test")
    public String test() {
        return "Api is working";
    }


}
