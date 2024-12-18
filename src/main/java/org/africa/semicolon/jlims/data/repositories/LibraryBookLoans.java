package org.africa.semicolon.jlims.data.repositories;

import org.africa.semicolon.jlims.data.models.LibraryBookLoan;
import org.africa.semicolon.jlims.data.models.User;
import org.africa.semicolon.jlims.dtos.response.BorrowBookResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibraryBookLoans extends MongoRepository<LibraryBookLoan, String> {
//    BorrowBookResponse releaseBooksWith(BorrowBookRequest borrowBookRequest, User user);
//    @Query("{ 'user': ?0, 'bookId': ?1 }")
//    BorrowBookResponse releaseBooksWith(BorrowBookRequest request, User user);
     LibraryBookLoan findByUserAndBookId(User user, String bookId);
     Boolean save(BorrowBookResponse borrowBookResponse);

}
