package org.africa.semicolon.jlims.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Borrower {
    @Id
    private String id;
    @DBRef
    private User member;
    @DBRef
    List<Book> books = new ArrayList<Book>();
    List<LibraryBookLoan> libraryBookLoans = new ArrayList<>();
}
