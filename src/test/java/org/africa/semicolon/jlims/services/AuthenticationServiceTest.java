package org.africa.semicolon.jlims.services;

import org.africa.semicolon.jlims.Enums.Role;
import org.africa.semicolon.jlims.data.models.Borrower;
import org.africa.semicolon.jlims.data.repositories.Books;
import org.africa.semicolon.jlims.data.repositories.Users;
import org.africa.semicolon.jlims.dtos.request.AccountRegisterRequest;
import org.africa.semicolon.jlims.dtos.response.AccountRegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;
    AccountRegisterRequest accountRegisterRequest;
    @Autowired
    private Users users;
    @Autowired
    private Books books;

    private Borrower borrower;

    @BeforeEach
    public void setUp() {
        users.deleteAll();
        books.deleteAll();

        accountRegisterRequest = new AccountRegisterRequest();
        accountRegisterRequest.setUsername("username");
        accountRegisterRequest.setPassword("password");
        accountRegisterRequest.setEmail("email@email.com");
        accountRegisterRequest.setCreatedAt(LocalDateTime.now());
        accountRegisterRequest.setLoggedIn(accountRegisterRequest.isLoggedIn());
        accountRegisterRequest.setRegistered(true);
        accountRegisterRequest.setName("John-Daniel Ikechukwu");
    }

    @Test
    public void registerUser_numberOfUser_isOneTest() {
        accountRegisterRequest.setRole(Role.MEMBER);
        AccountRegisterResponse response = authenticationService.register(accountRegisterRequest);
        assertNotNull(response);
    }

}