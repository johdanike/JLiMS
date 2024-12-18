package org.africa.semicolon.jlims.services;

import org.africa.semicolon.jlims.data.models.User;
import org.africa.semicolon.jlims.data.repositories.Users;
import org.africa.semicolon.jlims.dtos.request.*;
import org.africa.semicolon.jlims.dtos.response.*;
import org.africa.semicolon.jlims.exceptions.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Users users;

    public AuthenticationServiceImpl(Users users) {
        this.users = users;
    }

    @Override
    public AccountRegisterResponse register(AccountRegisterRequest registerRequest) {
        checkIfUserAlreadyExists(registerRequest.getUsername());
        User user = new User();
        user.setName(registerRequest.getUsername());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setRole(registerRequest.getRole());
        user.setLoggedIn(false);
        user.setRegistered(true);
        user.setCreatedAt(registerRequest.getCreatedAt());
        users.save(user);
        AccountRegisterResponse response = new AccountRegisterResponse();
        response.setUsername(user.getUsername());
        response.setMessage("Successfully registered");
        response.setId(user.getId());
        return response;
    }

    @Override
    public LogInResponse logIn(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public LogOutResponse logOut(LogOutRequest logOutRequest) {
        return null;
    }


    private void checkIfUserAlreadyExists(String username) {
        if(users.findByUsername(username) != null)
            throw new UserAlreadyExistsException("Username " + username + " already exists!");
    }


}
