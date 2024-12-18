package org.africa.semicolon.jlims.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BorrowBookRequest {
    private String bookId;
    private String bookName;
    private String author;
}
