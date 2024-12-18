package org.africa.semicolon.jlims.services;

import org.africa.semicolon.jlims.data.models.Book;
import org.africa.semicolon.jlims.data.models.Borrower;
import org.africa.semicolon.jlims.data.models.User;
import org.africa.semicolon.jlims.data.repositories.Books;
import org.africa.semicolon.jlims.data.repositories.Borrowers;
import org.africa.semicolon.jlims.data.repositories.Users;
import org.africa.semicolon.jlims.dtos.request.*;
import org.africa.semicolon.jlims.dtos.response.*;
import org.africa.semicolon.jlims.exceptions.BookAlreadyExistsException;
import org.africa.semicolon.jlims.exceptions.BookDetailsCannotBeEmptyException;
import org.africa.semicolon.jlims.exceptions.UserNameFieldCannotBeEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Users users;
    @Autowired
    private Books books;
    @Autowired
    private Borrowers borrowers;

    @Override
    public AccountRegisterResponse register(AccountRegisterRequest registerRequest) {
        usernameValidator(registerRequest);

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setRole(registerRequest.getRole());
        user.setRegistered(true);
        user.setLoggedIn(false);
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
//    @Override
//    public LogInResponse logIn(LoginRequest loginRequest) {
//        return null;
//    }
//
//    @Override
//    public LogOutResponse logOut(LogOutRequest logOutRequest) {
//        return null;
//    }

    @Override
    public AddBookResponse addBook(AddBookRequest addBookRequest) {
        BookExistsValidator(addBookRequest.getTitle(), addBookRequest.getAuthor());
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setBookType(addBookRequest.getBookType());
        book.setIsAvailable(addBookRequest.getIsAvailable());
        book.setQuantity(addBookRequest.getQuantity());
        books.save(book);

        AddBookResponse response = new AddBookResponse();
        response.setBookName(book.getTitle());
        response.setMessage("Book added successfully");
        return response;
    }

    private void BookExistsValidator(String title, String author) {
        if(title == null || author == null || author.trim().isEmpty()) {
            throw new BookDetailsCannotBeEmptyException("Title cannot be null");
        } else if (title.equalsIgnoreCase(books.findByTitle(title)) && author.equalsIgnoreCase(books.findByAuthor(author))) {
            throw new BookAlreadyExistsException("Book already exists");
        }
//        } else {
//            books.findByTitle(title);
//            books.findByAuthor(author);
//        }
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

    private Book getBook(){
        return books.findAll().getFirst();
    }
    private User getUser(){
        return users.findAll().getFirst();
    }
    private Borrower getBorrower(){
        return borrowers.findByMember(getUser());
    }


}
