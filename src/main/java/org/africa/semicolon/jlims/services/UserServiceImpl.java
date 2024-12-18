package org.africa.semicolon.jlims.services;

import org.africa.semicolon.jlims.data.models.Book;
import org.africa.semicolon.jlims.data.models.LibraryBookLoan;
import org.africa.semicolon.jlims.data.models.User;
import org.africa.semicolon.jlims.data.repositories.Books;
import org.africa.semicolon.jlims.data.repositories.Borrowers;
import org.africa.semicolon.jlims.data.repositories.LibraryBookLoans;
import org.africa.semicolon.jlims.data.repositories.Users;
import org.africa.semicolon.jlims.dtos.request.*;
import org.africa.semicolon.jlims.dtos.response.*;
import org.africa.semicolon.jlims.exceptions.BookAlreadyExistsException;
import org.africa.semicolon.jlims.exceptions.BookDetailsCannotBeEmptyException;
import org.africa.semicolon.jlims.exceptions.UserNameFieldCannotBeEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Users users;
    @Autowired
    private Books books;
    @Autowired
    private LibraryBookLoans libraryBookLoans;
    @Autowired
    private Borrowers borrowers;


    @Override
    public AccountRegisterResponse register(AccountRegisterRequest registerRequest) {
        usernameValidator(registerRequest);

        User user = getUserDetails(registerRequest);

        AccountRegisterResponse response = new AccountRegisterResponse();
        response.setUsername(user.getUsername());
        response.setId(user.getId());
        response.setMessage("User created successfully");
        return response;
    }

    private User getUserDetails(AccountRegisterRequest registerRequest) {
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
        return user;
    }

//    @Override
//    public LogInResponse logIn(LoginRequest loginRequest) {
//        return null;
//    }

//    @Override
//    public LogOutResponse logOut(LogOutRequest logOutRequest) {
//        return null;
//    }

    @Override
    public AddBookResponse addBook(AddBookRequest addBookRequest) {
        bookExistsValidator(addBookRequest.getTitle(), addBookRequest.getAuthor());
        Book book = getBookDetails(addBookRequest);

        AddBookResponse response = new AddBookResponse();
        response.setBookName(book.getTitle());
        response.setMessage("Book added successfully");
        response.setBookQuantity(book.getQuantity());
        return response;
    }

    private Book getBookDetails(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setBookType(addBookRequest.getBookType());
        book.setIsAvailable(addBookRequest.getIsAvailable());
        book.setQuantity(addBookRequest.getQuantity());
        books.save(book);
        return book;
    }

    @Override
    public BorrowBookResponse borrowBook(BorrowBookRequest borrowBookRequest) {
        User registeredMember = findUserBy(borrowBookRequest.getUsername());
        usernameValidator(registeredMember);

        newBookQuantityAfterBorrow(borrowBookRequest);

        String bookId = borrowBookRequest.getBookId();
        createNewBorrowBookRequest result = getCreateNewBorrowBookRequest(borrowBookRequest, bookId, registeredMember);
        libraryBookLoans.save(result.newLoan());

        LibraryBookLoan loan = libraryBookLoans.findByUserAndBookId(registeredMember, bookId);
        return loan != null ? result.response() : null;
    }

    private createNewBorrowBookRequest getCreateNewBorrowBookRequest(BorrowBookRequest borrowBookRequest, String bookId, User registeredMember) {
        BorrowBookResponse response = new BorrowBookResponse();
        response.setBookId(borrowBookRequest.getBookId());
        response.setMessage("Book borrowed successfully");
        response.setAuthor(borrowBookRequest.getAuthor());
        response.setBookName(borrowBookRequest.getBookName());
        response.setQuantity(borrowBookRequest.getQuantity() - 1);  // Adjust quantity for the response

        LibraryBookLoan newLoan = new LibraryBookLoan();
        newLoan.setBorrowDate(LocalDateTime.now());
        newLoan.setReturnDate(null);  // Set return date later
        newLoan.setBookId(borrowBookRequest.getBookId());
        newLoan.setQuantity(borrowBookRequest.getQuantity() - 1);  // Decrement quantity by 1 for the loan

        newLoan.setBorrower(borrowers.findByMember(registeredMember));

        return new createNewBorrowBookRequest(response, newLoan);
    }

    private void newBookQuantityAfterBorrow(BorrowBookRequest borrowBookRequest) {
        bookExistsValidator(borrowBookRequest.getBookName(), borrowBookRequest.getAuthor());

        Optional<Book> numOfBooks = books.findById(borrowBookRequest.getBookId());

        if (numOfBooks.isEmpty())
            throw new IllegalArgumentException("Book not found");
        int presentBookQuantity = numOfBooks.get().getQuantity();
        if (presentBookQuantity <= 0)
            throw new IllegalArgumentException("Book is currently out of stock, " +
                    "mind checking another book?");
        if (presentBookQuantity < borrowBookRequest.getQuantity())
            throw new IllegalArgumentException(("We currently have fewer copies available to borrow. " +
                    "Approx. %d copies available.").formatted(presentBookQuantity));

        numOfBooks.get().setQuantity(presentBookQuantity - borrowBookRequest.getQuantity());
        books.save(numOfBooks.get());
    }

    private record createNewBorrowBookRequest(BorrowBookResponse response, LibraryBookLoan newLoan) {
    }

    @Override
    public ReturnBookResponse returnBook(ReturnBookRequest returnBookRequest) {
        return null;
    }

    @Override
    public DeleteBookResponse deleteBook(DeleteBookRequest deleteBookRequest) {
        return null;
    }

//    private User getUser(){
//        return users.findAll().getFirst();
//    }
//    private Borrower getBorrower(){
//        return borrowers.findByMember(getUser());
//    }

    private void validateThatUserIsLoggedIn(User user){
        if(!user.isLoggedIn()){
            throw new IllegalArgumentException("User is not logged in");
        }
    }

    private User findUserBy(String username){
        return users.findByUsername(username);
    }

    private void bookExistsValidator(String title, String author) {
        if(title == null || author == null || author.trim().isEmpty()) {
            throw new BookDetailsCannotBeEmptyException("Title cannot be null");
        } else if (title.equalsIgnoreCase(books.findByTitle(title)) && author.equalsIgnoreCase(books.findByAuthor(author))) {
            throw new BookAlreadyExistsException("Book already exists");
        }
    }

    private void usernameValidator(AccountRegisterRequest registerRequest) {
        if (registerRequest.getUsername() == null || registerRequest.getUsername().trim().isEmpty()) {
            throw new UserNameFieldCannotBeEmptyException("Username cannot be null");
        }
    }

    private void usernameValidator(User user){
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new UserNameFieldCannotBeEmptyException("Username cannot be null");
        }
    }


}
