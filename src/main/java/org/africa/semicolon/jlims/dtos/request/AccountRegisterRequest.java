package org.africa.semicolon.jlims.dtos.request;

import lombok.Getter;
import lombok.Setter;
import org.africa.semicolon.jlims.Enums.Role;

@Setter
@Getter
public class AccountRegisterRequest {
    private String name;
    private String email;
    private String username;
    private String password;
    private Role role;
    private boolean isLoggedIn;
}
