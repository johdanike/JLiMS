package org.africa.semicolon.jlims.data.models;

import lombok.Data;
import org.africa.semicolon.jlims.Enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String username;
    private String password;
    private Role role;
    private boolean isLoggedIn;

}
