package org.africa.semicolon.jlims.services;

import org.africa.semicolon.jlims.Enums.BookType;
import org.africa.semicolon.jlims.Enums.Role;
import org.africa.semicolon.jlims.data.models.Borrower;
import org.africa.semicolon.jlims.data.repositories.Books;
import org.africa.semicolon.jlims.data.repositories.Borrowers;
import org.africa.semicolon.jlims.data.repositories.Users;
import org.africa.semicolon.jlims.dtos.request.AccountRegisterRequest;
import org.africa.semicolon.jlims.dtos.request.AddBookRequest;
import org.africa.semicolon.jlims.dtos.request.BorrowBookRequest;
import org.africa.semicolon.jlims.dtos.response.AccountRegisterResponse;
import org.africa.semicolon.jlims.dtos.response.AddBookResponse;
import org.africa.semicolon.jlims.dtos.response.BorrowBookResponse;
import org.africa.semicolon.jlims.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    AccountRegisterRequest accountRegisterRequest;
    @Autowired
    private Users users;
    @Autowired
    private Books books;
    @Autowired
    private Borrowers borrowers;

    private AddBookRequest addBookRequest;
    private BorrowBookRequest borrowBookRequest;

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

        addBookRequest = new AddBookRequest();
        addBookRequest.setTitle("Things fall apart");
        addBookRequest.setAuthor("Chinua Achebe");
        addBookRequest.setBookType(BookType.DRAMA);
        addBookRequest.setIsAvailable("Yes");
        addBookRequest.setQuantity(10);

        borrowBookRequest = new BorrowBookRequest();
        borrowBookRequest.setName(accountRegisterRequest.getName());
        borrowBookRequest.setRole(Role.MEMBER);
        borrowBookRequest.setBookId(borrowBookRequest.getBookId());
        borrowBookRequest.setUsername(accountRegisterRequest.getUsername());
        borrowBookRequest.setBookType(addBookRequest.getBookType());
        borrowBookRequest.setQuantity(addBookRequest.getQuantity());
        borrowBookRequest.setBookName("Things fall apart");
        borrowBookRequest.setAuthor("Chinua Achebe");

    }

    @Test
    public void registerUser_numberOfUser_isOne_Test() {
        accountRegisterRequest.setRole(Role.MEMBER);
        AccountRegisterResponse response = userService.register(accountRegisterRequest);
        assertNotNull(response);
        assertEquals(1L, users.count());
    }

    @Test
    public void i_registerUserTwice_countIsStillOne_Test() {
        userService.register(accountRegisterRequest);
        assertEquals(1L, users.count());
    }

    @Test
    public void i_registerUserTwice_throwException_Test() {
        userService.register(accountRegisterRequest);
        try{
            userService.register(accountRegisterRequest);
        }catch (UserAlreadyExistsException e){
            assertEquals(e.getMessage(), "User already exists");
        }
    }

    @Test
    public void iAddOneBook_bookCountIsOne_Test() {
        userService.register(accountRegisterRequest);
        assertEquals(0L, books.count());

        AddBookResponse addBookResponse = userService.addBook(addBookRequest);
        assertEquals("Book added successfully", addBookResponse.getMessage());
        assertEquals(1L, books.count());
    }

    @Test
    public void i_addABookThatIs10InNumber_bookCountIs1_quantityIs10_test(){
        userService.register(accountRegisterRequest);
        assertEquals(0L, books.count());

        AddBookResponse addBookResponse = userService.addBook(addBookRequest);
        assertEquals("Book added successfully", addBookResponse.getMessage());
        assertEquals(1L, books.count());

        Integer bookQuantity = addBookResponse.getBookQuantity();
        assertEquals(10 , bookQuantity);
    }

    @Test
    public void i_borrowExistingBook_bookCountIsStill1_bookQuantityDecreasesByOne_Test() {
        accountRegisterRequest.setRole(Role.MEMBER);
        userService.register(accountRegisterRequest);
        assertEquals(0L, books.count());

        AddBookResponse addBookResponse = userService.addBook(addBookRequest);
        assertEquals("Book added successfully", addBookResponse.getMessage());

        System.out.println(borrowBookRequest.getBookId());

        BorrowBookResponse borrowBookResponse = userService.borrowBook(borrowBookRequest);
        assertEquals("Book borrowed successfully", borrowBookResponse.getMessage());
        assertEquals(1L, books.count());
        assertEquals(9, addBookResponse.getBookQuantity());
    }


}