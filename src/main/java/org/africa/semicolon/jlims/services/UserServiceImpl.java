package org.africa.semicolon.jlims.services;

import org.africa.semicolon.jlims.data.models.User;
import org.africa.semicolon.jlims.data.repositories.Users;
import org.africa.semicolon.jlims.dtos.request.*;
import org.africa.semicolon.jlims.dtos.response.*;
import org.africa.semicolon.jlims.exceptions.UserNameFieldCannotBeEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Users users;

    @Override
    public AccountRegisterResponse register(AccountRegisterRequest registerRequest) {
        usernameValidator(registerRequest);

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setRole(registerRequest.getRole());
        user.setRegistered(true);
        user.setLoggedIn(true);
        user.setCreatedAt(registerRequest.getCreatedAt());
        user.setLastLogin(LocalDateTime.now());
        users.save(user);

        AccountRegisterResponse response = new AccountRegisterResponse();
        response.setUsername(user.getUsername());
        response.setId(user.getId());
        response.setMessage("User created successfully");
        return response;
    }

    private void usernameValidator(AccountRegisterRequest registerRequest) {
        if (registerRequest.getUsername() == null || registerRequest.getUsername().trim().isEmpty()) {
            throw new UserNameFieldCannotBeEmptyException("Username cannot be null");
        }
    }
    @Override
    public LogInResponse logIn(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public LogOutResponse logOut(LogOutRequest logOutRequest) {
        return null;
    }

    @Override
    public AddBookResponse addBook(AddBookRequest addBookRequest) {
        return null;
    }

    @Override
    public BorrowBookResponse borrowBook(BorrowBookRequest borrowBookRequest) {
        return null;
    }

    @Override
    public ReturnBookResponse returnBook(ReturnBookRequest returnBookRequest) {
        return null;
    }

    @Override
    public DeleteBookResponse deleteBook(DeleteBookRequest deleteBookRequest) {
        return null;
    }
}
