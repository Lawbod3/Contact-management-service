package org.BodeNetwork.com.data.repositories;

import org.BodeNetwork.com.data.models.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String> {
}
