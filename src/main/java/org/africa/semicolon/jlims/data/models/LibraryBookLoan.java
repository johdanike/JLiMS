package org.africa.semicolon.jlims.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Document
public class LibraryBookLoan {
    @Id
    private String id;
    @DBRef
    private Borrower borrower;
    @DBRef
    private Book book;
    private User user;
    @DBRef
    private String bookId;
    private LocalDateTime borrowDate = LocalDateTime.now();
    private LocalDateTime returnDate;
    private Integer quantity;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH:mm:ss");
        String borrowDateString = borrowDate.format(formatter);
        String returnDateString = (returnDate != null) ? returnDate.format(formatter) : "Yet to be returned";

        String format = "LibraryBookLoan{\nid = '%s'\nbook id = '%s'\nborrowed at = '%s'\nreturn date = '%s'\n}";
        return String.format(format, id, book.getId(), borrowDateString, returnDateString);
    }
}
