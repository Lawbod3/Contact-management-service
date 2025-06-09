package org.BodeNetwork.com.controllers;

import org.BodeNetwork.com.data.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/BodeNetwork-contact")
public class ContactControllers {
    @Autowired
    ContactRepository contactRepository;

    @GetMapping("/test")
    public String test() {
        return "Api is working";
    }

}
