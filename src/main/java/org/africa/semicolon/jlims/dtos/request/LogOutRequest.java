package org.africa.semicolon.jlims.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogOutRequest {
    private String username;
    private String password;
    private String message;
}
