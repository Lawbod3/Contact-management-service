package org.BodeNetwork.com.controllers;

import jakarta.validation.Valid;
import org.BodeNetwork.com.dtos.request.AddContactRequest;
import org.BodeNetwork.com.dtos.request.DeleteContactRequest;
import org.BodeNetwork.com.dtos.request.UpdateContactRequest;
import org.BodeNetwork.com.dtos.response.AddContactResponse;
import org.BodeNetwork.com.dtos.response.ApiResponse;
import org.BodeNetwork.com.dtos.response.DeleteContactResponse;
import org.BodeNetwork.com.dtos.response.UpdateContactResponse;
import org.BodeNetwork.com.exceptions.ContactDoesNotExistException;
import org.BodeNetwork.com.exceptions.UserDoesNotExistException;
import org.BodeNetwork.com.services.ContactManagementService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/BodeNetwork-contact")
public class ContactControllers {

    @Autowired
    ContactManagementService contactManagementService;

    @GetMapping("/test")
    public String test() {
        return "Api is working";
    }
    @PostMapping("/add")
    public ResponseEntity<?> addContact(@Valid @RequestBody AddContactRequest addContactRequest) {
        try {
            AddContactResponse addContactResponse = contactManagementService.addContact(addContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, addContactResponse), HttpStatus.CREATED);
        }
        catch (UserDoesNotExistException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteContact( @Valid @RequestBody DeleteContactRequest deleteContactRequest) {
        try{

            DeleteContactResponse deleteContactResponse = contactManagementService.deleteContact(deleteContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, deleteContactResponse), HttpStatus.OK);
        }
        catch (ContactDoesNotExistException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateContact( @Valid @RequestBody UpdateContactRequest updateContactRequest) {
        try {
            UpdateContactResponse updateContactResponse = contactManagementService.updateContact(updateContactRequest);
            return new ResponseEntity<>(new ApiResponse(true, updateContactResponse), HttpStatus.OK);
        }
        catch (ContactDoesNotExistException e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {

            return new ResponseEntity<>(new ApiResponse(false, "Internal server error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private ResponseEntity<?> createErrorResponse(Exception e, HttpStatus status) {
        return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), status);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        FieldError errorMessage = e.getBindingResult().getFieldError();
        return createErrorResponse(new Exception(errorMessage.getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return createErrorResponse(new Exception("HTTP method not supported: " + e.getMethod()), HttpStatus.METHOD_NOT_ALLOWED); // 405
    }



}
