package com.ada.demo.controllers.impl;

import com.ada.demo.controllers.EmailController;
import com.ada.demo.entities.Email;
import com.ada.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmailControllerImpl implements EmailController {
    private int contador = 0;
    private boolean emailValid = false;

    @Autowired
    EmailService emailService;

    @Override
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        String msg = "Funciona!";
        HttpHeaders header = new HttpHeaders();
        header.add("titulo", "Validator email application");
        return new ResponseEntity<String>(msg, header, HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/emailsValid")
    public ResponseEntity<List<Email>> getValidEmails() {
        List<Email> validEmails = emailService.findValidEmails();
        HttpHeaders header  = new HttpHeaders();
        header.add("titulo", "Lista de emails válidos");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(validEmails);
    }

    @Override
    @GetMapping(value = "/emailsNotValid")
    public ResponseEntity<List<Email>> getInvalidEmails() {
        List<Email> emailsNotValid = emailService.findInvalidEmails();
        return ResponseEntity.status(HttpStatus.OK).body(emailsNotValid);
    }

    @Override
    @GetMapping(value = "/emails/{id}")
    public ResponseEntity<Optional<Email>> getEmailById(@PathVariable Long id) {
        Optional<Email> emailById = emailService.findEmailById(id);
        return ResponseEntity.ok(emailById);
    }

    @Override
    @GetMapping("/emails/validate/{email}")
    public ResponseEntity<String> validarEmail(@PathVariable("email") String email) {
        contador++;
        if(emailService.validarEmail(email)){
            emailValid = true;
            String msg = "Es válido";
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
        } else {
            emailValid = false;
            String msg = "No es válido el mail ingresado";
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        }
    }


    @GetMapping("/emails/validate/intentos")
    public ResponseEntity<String> getIntentos(){
        int auxContador = contador;
        String msg = emailService.contadorRegistros(emailValid, auxContador);
        if(emailValid){
            contador = 0;
        }
        return ResponseEntity.ok(msg);
    }

    @Override
    @PostMapping(value = "/emails/validate/add")
    public ResponseEntity<Email> addEmail(@RequestBody Email email) {
        if(emailValid){
            Email newEmail = emailService.saveEmail(emailValid, email);
            HttpHeaders header = new HttpHeaders();
            header.add("description", "adding new email");
            return ResponseEntity.status(HttpStatus.CREATED).headers(header).body(newEmail);
        } return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Email());
    }
}
