package org.africa.semicolon.jlims.services;

import org.africa.semicolon.jlims.dtos.request.*;
import org.africa.semicolon.jlims.dtos.response.*;

public interface AuthenticationService {
    AccountRegisterResponse register(AccountRegisterRequest request);
    boolean logIn(LoginRequest loginRequest);
    boolean logOut();
}

