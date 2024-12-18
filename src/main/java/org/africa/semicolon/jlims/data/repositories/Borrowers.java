package org.africa.semicolon.jlims.data.repositories;

import org.africa.semicolon.jlims.data.models.Borrower;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Borrowers extends MongoRepository<Borrower, String> {
}
