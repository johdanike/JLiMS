package org.africa.semicolon.jlims.data.repositories;

import org.africa.semicolon.jlims.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Users extends MongoRepository<User, String> {
}