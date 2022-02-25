package com.ada.demo.controllersImpl;

import com.ada.demo.controllers.EmailController;
import com.ada.demo.entities.Email;
import com.ada.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String test() {
        return "Funciona!";
    }

    @Override
    @GetMapping(value = "/emailsValid")
    public List<Email> getValidEmails() {
        return emailService.findValidEmails();
    }

    @Override
    @GetMapping(value = "/emailsNotValid")
    public List<Email> getInvalidEmails() {
        return emailService.findInvalidEmails();
    }

    @Override
    @GetMapping(value = "/emails/{id}")
    public Optional<Email> getEmailById(@PathVariable Long id) {
        return emailService.findEmailById(id);
    }

    @Override
    @GetMapping("/emails/validate/{email}")
    public String validarEmail(@PathVariable String email) {
        contador++;
        if(emailService.validarEmail(email)){
            emailValid = true;
            return "Es válido";
        } else {
            emailValid = false;
            return "No es válido el mail ingresado";
        }
    }

    @GetMapping("/emails/validate/intentos")
    public String getIntentos(){
        int auxContador = contador;
        if(emailValid){
            contador = 0;
        }
        return emailService.contadorRegistros(emailValid, auxContador);
    }

    @Override
    @PostMapping(value = "/emails/validate/add")
    public String addEmail(@RequestBody Email email) {
        if(emailValid){
            emailService.saveEmail(emailValid, email);
            return "Guardado satisfactoriamente";
        } else {
            return "Debe comprobar un email válido o el email es nulo";
        }
    }
}
