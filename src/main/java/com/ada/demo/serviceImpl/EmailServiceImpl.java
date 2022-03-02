package com.ada.demo.serviceImpl;

import com.ada.demo.entities.Email;
import com.ada.demo.repository.EmailRepository;
import com.ada.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.LocalDate;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Override
    public Optional<Email> findEmailById(Long id) {
        Optional<Email> email = emailRepository.findById(id);
        return email;
    }

    @Override
    public List<Email>findValidEmails() {
        List<Email> emailsValidos = new ArrayList<>();
        List<Email> emails = emailRepository.findAll();
        for (Email emailValid : emails) {
            if (emailValid.isValido()) {
                emailsValidos.add(emailValid);
            }
        }
        return emailsValidos;
    }

    @Override
    public List<Email> findInvalidEmails() {
        List<Email> emailsNoValidos = new ArrayList<>();
        List<Email> emails = emailRepository.findAll();
        for (Email emailInvalid : emails) {
            if (!emailInvalid.isValido()) {
                emailsNoValidos.add(emailInvalid);
            }
        }
        return emailsNoValidos;
    }

    @Override
    public boolean validarEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#^_'~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public Email saveEmail(boolean emailValid, Email emailNew) {
        if (emailNew != null && emailValid) {
            emailNew.setValido(emailValid);
            emailNew.setFecha(LocalDate.now());
            emailRepository.save(emailNew);
        }
        return emailNew;
    }

    @Override
    public String contadorRegistros(boolean emailValid, int intentos){
        LocalDate fecha = LocalDate.now();
        if(emailValid){
            return "Validaste el email en " + intentos + " intentos. Fecha " + fecha;
        } else {
            return "Llevas " + intentos + " intentos. Fecha " + fecha;
        }
    }
}
