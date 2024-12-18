package org.africa.semicolon.jlims.services;

import org.africa.semicolon.jlims.data.models.Borrower;
import org.africa.semicolon.jlims.data.repositories.Books;
import org.africa.semicolon.jlims.data.repositories.Users;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    private Users users;
    private Books books;
    private Borrower borrower;

    @BeforeEach
    public void setUp() {
        users.deleteAll();
        books.deleteAll();
    }



}