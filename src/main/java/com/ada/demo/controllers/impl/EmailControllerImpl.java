package com.ada.demo.controllers.impl;

import com.ada.demo.controllers.EmailController;
import com.ada.demo.entities.Email;
import com.ada.demo.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(tags = "Usuarios")
@RestController
@RequestMapping("/")
public class EmailControllerImpl implements EmailController {
    private int contador = 0;
    private boolean emailValid = false;

    @Autowired
    EmailService emailService;

    @ApiOperation(
            value = "Devuelve una cadena de texto",
            notes = "Verifica que la api responde, si retorna <b>Funciona</b> se pueden usar los servicios de la api. De otro modo, revisar"
    )
    @Override
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        String msg = "Funciona!";
        HttpHeaders header = new HttpHeaders();
        header.add("titulo", "Validator email application");
        return new ResponseEntity<String>(msg, header, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Lista de emails válidos"
    )
    @Override
    @GetMapping(value = "/emailsValid")
    public ResponseEntity<List<Email>> getValidEmails() {
        List<Email> validEmails = emailService.findValidEmails();
        HttpHeaders header  = new HttpHeaders();
        header.add("titulo", "Lista de emails válidos");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(validEmails);
    }

    @ApiOperation(
            value = "Lista de emails no válidos"
    )
    @Override
    @GetMapping(value = "/emailsNotValid")
    public ResponseEntity<List<Email>> getInvalidEmails() {
        List<Email> emailsNotValid = emailService.findInvalidEmails();
        return ResponseEntity.status(HttpStatus.OK).body(emailsNotValid);
    }

    @ApiOperation(
            value = "Email según id"
    )
    @Override
    @GetMapping(value = "/emails/{id}")
    public ResponseEntity<Optional<Email>> getEmailById(@PathVariable Long id) {
        Optional<Email> emailById = emailService.findEmailById(id);
        return ResponseEntity.ok(emailById);
    }

    @ApiOperation(
            value = "Devuelve un texto",
            notes = "Si el email es válido retorna <b>es válido</b>, si no lo es retorna <b>el email no es válido</b>"
    )
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

    @ApiOperation(
            value = "Devuelve el número de intentos",
            notes = "Desde que se envía un email para verificar si es válido, la api cuenta la cantidad de intentos hasta que el email sea válido"
    )
    @GetMapping("/emails/validate/intentos")
    public ResponseEntity<String> getIntentos(){
        int auxContador = contador;
        String msg = emailService.contadorRegistros(emailValid, auxContador);
        if(emailValid){
            contador = 0;
        }
        return ResponseEntity.ok(msg);
    }

    @ApiOperation(
            value = "Guarda email verificado",
            notes = "Si se comprobó que el email es válido, entonces se tiene la opción de crear y guardar un usuario en la base de datos, enviando solo el email que se verificó"
    )
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
