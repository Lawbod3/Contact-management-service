package org.BodeNetwork.com.data.repositories;

import org.BodeNetwork.com.data.models.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends MongoRepository<Contact, String> {
    List<Contact> findByUserId(String UserId);
}
