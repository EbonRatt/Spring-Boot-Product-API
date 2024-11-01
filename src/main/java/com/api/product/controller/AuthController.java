package com.api.product.controller;

import com.api.product.model.entities.User;
import com.api.product.model.reponse.AuthResponse;
import com.api.product.model.reponse.ProductResponse;
import com.api.product.model.request.RegisterRequest;
import com.api.product.model.request.UserRequest;
import com.api.product.repository.UserRepository;
import com.api.product.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> Login(@RequestBody UserRequest userRequest){
        try{
            String jwtToken = authService.Login(userRequest);
            return ResponseEntity.ok(ResponseBack("Your Token",jwtToken,HttpStatus.OK));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseBack("Wrong Name Or Password!","",HttpStatus.NOT_FOUND));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> Register(@RequestBody RegisterRequest registerRequest){
        try{
            String jwtToken = authService.Register(registerRequest);
            return ResponseEntity.ok(ResponseBack("Register Successful",jwtToken,HttpStatus.OK));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ResponseBack("Failed To Register","",HttpStatus.NOT_ACCEPTABLE));
        }
    }

    private AuthResponse ResponseBack(String message, String token, HttpStatus httpStatus){
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage(message);
        authResponse.setToken(token);
        authResponse.setHttpStatus(httpStatus);
        authResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return authResponse;
    }
}
