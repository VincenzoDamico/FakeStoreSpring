package org.springdemo.progetto.controllers;

import lombok.AllArgsConstructor;
import org.springdemo.progetto.entities.User;
import org.springdemo.progetto.services.AccountingService;
import org.springdemo.progetto.services.KeycloakService;
import org.springdemo.progetto.support.MyConstant;
import org.springdemo.progetto.support.ResponseMessage;
import org.springdemo.progetto.support.UserRegistrationRequest;
import org.springdemo.progetto.support.exeception.FieldIncorrectException;
import org.springdemo.progetto.support.exeception.MailUserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ApiUsers")
@AllArgsConstructor
public class AccountingController {
    @Autowired
    private AccountingService accountingService;

    private KeycloakService keycloakService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserRegistrationRequest req) {

        try {
            if (req == null) {
                return new ResponseEntity<>(MyConstant.ERR_PARMAM, HttpStatus.BAD_REQUEST);
            }
            User res = req.getUser();
            if (res == null) {
                return new ResponseEntity<>(MyConstant.ERR_PARMAM, HttpStatus.BAD_REQUEST);
            }
            res = keycloakService.addUser(res, req.getPassword());
            if (res == null) {
                return new ResponseEntity<>(MyConstant.ERR_KEYCLOAK, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (MailUserAlreadyExistsException e) {
            return new ResponseEntity<>(new ResponseMessage(MyConstant.ERR_EMAIL), HttpStatus.BAD_REQUEST);
        } catch (FieldIncorrectException e) {
            return new ResponseEntity<>(MyConstant.FIELD_INCORRECT, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<User> getAll() {
        return accountingService.getAllUsers();
    }


}