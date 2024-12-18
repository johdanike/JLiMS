package org.africa.semicolon.jlims.dtos.request;

import lombok.Getter;
import lombok.Setter;
import org.africa.semicolon.jlims.Enums.BookType;

@Setter
@Getter
public class BorrowBookRequest {
    private String username;
    private String bookId;
    private String bookName;
    private String author;
    private BookType bookType;
    private Integer quantity;
}
