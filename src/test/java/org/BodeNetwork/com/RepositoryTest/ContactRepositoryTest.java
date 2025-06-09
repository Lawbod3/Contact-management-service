package org.BodeNetwork.com.RepositoryTest;

import org.BodeNetwork.com.data.models.Contact;
import org.BodeNetwork.com.data.repositories.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
public class ContactRepositoryTest {
    @Autowired
    ContactRepository contactRepository;

    @BeforeEach
    public void setUp() {
        contactRepository.deleteAll();
    }
    @Test
    public void testContactRepositoryIsEmpty() {
        assertTrue(contactRepository.findAll().isEmpty());
    }

    @Test
    public void testContactRepositoryIsNotEmpty() {
        Contact contact = new Contact();
        contactRepository.save(contact);
        assertTrue(!contactRepository.findAll().isEmpty());
    }

    @Test
    public void testContactRepositoryContainsContact() {
        Contact contact = new Contact();
        contact.setEmail("test@test.com");
        contact.setLastname("testFirstname");
        contact.setLastname("testLastName");
        contact.setUserId("testId");
        contactRepository.save(contact);
        List<Contact> findContacts = contactRepository.findByUserId("testId");
        assertTrue(findContacts.contains(contact));
    }


}
