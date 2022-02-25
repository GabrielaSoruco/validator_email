package com.ada.demo.controllers;

import com.ada.demo.entities.Email;

import java.util.List;
import java.util.Optional;

public interface EmailController {

    List<Email> getValidEmails();
    List<Email> getInvalidEmails();
    Optional<Email> getEmailById(Long id);
    String addEmail(Email email);
    String test();
    String validarEmail(String email);

}
