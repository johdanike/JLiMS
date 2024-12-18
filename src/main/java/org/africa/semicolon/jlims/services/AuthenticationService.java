package org.africa.semicolon.jlims.services;

import org.africa.semicolon.jlims.dtos.request.*;
import org.africa.semicolon.jlims.dtos.response.*;

public interface AuthenticationService {
    AccountRegisterResponse register(AccountRegisterRequest request);
    LogInResponse logIn(LoginRequest loginRequest);
    LogOutResponse logOut(LogOutRequest logOutRequest);
}

