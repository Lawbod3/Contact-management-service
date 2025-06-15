package org.BodeNetwork.com.controllers;

import jakarta.validation.Valid;
import org.BodeNetwork.com.data.repositories.UserRepository;
import org.BodeNetwork.com.dtos.request.UserLoginRequest;
import org.BodeNetwork.com.dtos.request.UserRegistrationRequest;
import org.BodeNetwork.com.dtos.response.ApiResponse;
import org.BodeNetwork.com.dtos.response.UserLoginResponse;
import org.BodeNetwork.com.dtos.response.UserRegistrationResponse;
import org.BodeNetwork.com.exceptions.PasswordException;
import org.BodeNetwork.com.exceptions.UserDoesNotExistException;
import org.BodeNetwork.com.exceptions.UserExistException;
import org.BodeNetwork.com.services.ContactManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/BodeNetwork-user")
public class UserControllers {
    @Autowired
    ContactManagementService contactManagementService;

    @GetMapping("/test")
    public String test() {
        return "Api is working";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationRequest   request) {
        try {
            UserRegistrationResponse response = contactManagementService.registerUser(request);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.CREATED);
        }
        catch (UserExistException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest request) {
        try {
            UserLoginResponse response = contactManagementService.loginUser(request);
            return new ResponseEntity<>(new ApiResponse(true, response), HttpStatus.OK);
        }
        catch (UserDoesNotExistException | PasswordException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<?> createErrorResponse(Exception e, HttpStatus status) {
        return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), status);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        return createErrorResponse(new Exception(fieldError.getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }



}
