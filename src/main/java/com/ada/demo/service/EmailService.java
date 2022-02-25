package com.ada.demo.service;

import com.ada.demo.entities.Email;
import java.util.List;

import java.util.Optional;

public interface EmailService {

    Optional<Email> findEmailById(Long id);
    void saveEmail(boolean emilValid, Email emailNew);
    boolean validarEmail(String email);
    List<Email> findValidEmails();
    List<Email> findInvalidEmails();
    String contadorRegistros(boolean emailValid, int intentos);
}
