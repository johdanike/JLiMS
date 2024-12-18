package org.africa.semicolon.jlims.data.repositories;

import org.africa.semicolon.jlims.data.models.LibraryBookLoan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibraryBookLoans extends MongoRepository<LibraryBookLoan, String> {
}
