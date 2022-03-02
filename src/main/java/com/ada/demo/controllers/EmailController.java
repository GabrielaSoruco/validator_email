package com.ada.demo.controllers;

import com.ada.demo.entities.Email;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface EmailController {

    ResponseEntity<List<Email>> getValidEmails();
    ResponseEntity<List<Email>> getInvalidEmails();
    ResponseEntity<Optional<Email>> getEmailById(Long id);
    ResponseEntity<Email> addEmail(Email email);
    ResponseEntity<String> test();
    ResponseEntity<String> validarEmail(String email);

}
