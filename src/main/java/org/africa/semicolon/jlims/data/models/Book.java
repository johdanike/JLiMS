package org.africa.semicolon.jlims.data.models;

import lombok.Data;
import org.africa.semicolon.jlims.Enums.BookType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private BookType bookType;
    private Integer quantity;
    private String isAvailable;

    @Override
    public String toString() {
        String format = "Book{\nid = '%s'\nauthor = '%s'\nbookType = '%s'\nquantity = %d\nisAvailable = '%s'\n}";
        return String.format(format, id, author, bookType, quantity, isAvailable);
    }
}
