package org.africa.semicolon.jlims.services;

import org.africa.semicolon.jlims.data.repositories.Users;
import org.africa.semicolon.jlims.dtos.request.AccountRegisterRequest;
import org.africa.semicolon.jlims.dtos.request.LoginRequest;
import org.africa.semicolon.jlims.dtos.response.AccountRegisterResponse;
import org.africa.semicolon.jlims.dtos.response.LogOutResponse;
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
    Users users;

    @BeforeEach
    public void setUp() {
        users.deleteAll();

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
    public void testRegisterAccount() {
        accountRegisterRequest.setLoggedIn(false);
        AccountRegisterResponse accountRegisterResponse = authenticationService.register(accountRegisterRequest);
        assertNotNull(accountRegisterResponse);
        assertEquals(1, users.count());
    }

    @Test
    public void test_thatUserCanLogin() {
        accountRegisterRequest.setLoggedIn(false);
        AccountRegisterResponse accountRegisterResponse = authenticationService.register(accountRegisterRequest);
        assertNotNull(accountRegisterResponse);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(accountRegisterRequest.getUsername());
        loginRequest.setPassword(accountRegisterRequest.getPassword());
        boolean loginResponse = authenticationService.logIn(loginRequest);
        assertTrue(loginResponse);
        assertEquals(1, users.count());
    }

    @Test
    public void test_thatUserCanLogOut() {
        accountRegisterRequest.setLoggedIn(false);
        AccountRegisterResponse accountRegisterResponse = authenticationService.register(accountRegisterRequest);
        assertNotNull(accountRegisterResponse);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(accountRegisterRequest.getUsername());
        loginRequest.setPassword(accountRegisterRequest.getPassword());
        boolean loginResponse = authenticationService.logIn(loginRequest);
        assertTrue(loginResponse);
        boolean logoutResponse = authenticationService.logOut();
        assertTrue(logoutResponse);
    }

    @Test
    public void test_thatUserCannotLoginWithWrongUsername_throwsException() {
        accountRegisterRequest.setLoggedIn(false);
        AccountRegisterResponse accountRegisterResponse = authenticationService.register(accountRegisterRequest);
        assertNotNull(accountRegisterResponse);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Lagbaja");
        loginRequest.setPassword("password");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.logIn(loginRequest);
        });

        assertEquals("Invalid username or password", thrown.getMessage());
    }

    @Test
    public void test_thatUserCannotRegisterSameNameMoreThanOnce_throwsException() {

    }






}