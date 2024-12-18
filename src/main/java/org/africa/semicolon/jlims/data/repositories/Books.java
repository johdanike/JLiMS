package org.africa.semicolon.jlims.data.repositories;

import org.africa.semicolon.jlims.data.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Books extends MongoRepository<Book, String> {
    String findByTitle(String title);
    String findByAuthor(String author);
}
