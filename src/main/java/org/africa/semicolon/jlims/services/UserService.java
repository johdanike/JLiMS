package org.africa.semicolon.jlims.services;

import org.africa.semicolon.jlims.dtos.request.*;
import org.africa.semicolon.jlims.dtos.response.*;

public interface UserService {
    AccountRegisterResponse register(AccountRegisterRequest registerRequest);
//    LogInResponse logIn(LoginRequest loginRequest);
//    LogOutResponse logOut(LogOutRequest logOutRequest);
    AddBookResponse addBook(AddBookRequest addBookRequest);
    BorrowBookResponse borrowBook(BorrowBookRequest borrowBookRequest);
    ReturnBookResponse returnBook(ReturnBookRequest returnBookRequest);
    DeleteBookResponse deleteBook(DeleteBookRequest deleteBookRequest);
}
