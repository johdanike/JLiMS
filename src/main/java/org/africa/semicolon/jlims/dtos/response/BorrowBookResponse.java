package org.africa.semicolon.jlims.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BorrowBookResponse {
    private String bookId;
    private String bookName;
    private String author;
    private String message;
}
