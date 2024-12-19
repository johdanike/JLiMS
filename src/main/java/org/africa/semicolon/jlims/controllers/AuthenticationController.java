package org.africa.semicolon.jlims.controllers;

import lombok.RequiredArgsConstructor;
import org.africa.semicolon.jlims.dtos.request.AccountRegisterRequest;
import org.africa.semicolon.jlims.dtos.request.LoginRequest;
import org.africa.semicolon.jlims.dtos.response.AccountApiResponse;
import org.africa.semicolon.jlims.dtos.response.AccountRegisterResponse;
import org.africa.semicolon.jlims.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody AccountRegisterRequest accountRegisterRequest){
        try{
            AccountRegisterResponse accountRegisterResponse = authenticationService.register(accountRegisterRequest);
            return new ResponseEntity<>(new AccountApiResponse(true, accountRegisterResponse), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            boolean loginResponse = authenticationService.logIn(loginRequest);
            return new ResponseEntity<>(new AccountApiResponse(loginResponse, loginRequest), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        try{
            boolean logoutResponse = authenticationService.logOut();
            return new ResponseEntity<>(logoutResponse, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
