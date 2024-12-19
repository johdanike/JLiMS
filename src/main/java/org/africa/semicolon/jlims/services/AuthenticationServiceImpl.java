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
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setMessage("Successfully registered");
        return response;
    }

    @Override
    public boolean logIn(LoginRequest loginRequest) {
        User user = this.users.findByUsername(loginRequest.getUsername());
        if (user == null || loginRequest.getUsername() == null || !user.getPassword().equals(loginRequest.getPassword())

                || !user.getUsername().equals(loginRequest.getUsername())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        user.setLoggedIn(true);
        System.out.println("Logged In Successfully");
        return user.isLoggedIn();
    }

    @Override
    public boolean logOut() {
        User user = getCurrentUser();
        user.setLoggedIn(false);
        users.save(user);
        return true;
    }

    private User getCurrentUser() {
        User user = new User();
        if(users.findByUsername(user.getUsername()) != null || user.isLoggedIn()) {
            return users.findByUsername(user.getUsername());
        }
        return user;
    }


    private void checkIfUserAlreadyExists(String username) {
        if(users.findByUsername(username) != null)
            throw new UserAlreadyExistsException("Username " + username + " already exists!");
    }


}
