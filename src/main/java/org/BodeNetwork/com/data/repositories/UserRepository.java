package org.BodeNetwork.com.data.repositories;

import org.BodeNetwork.com.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
