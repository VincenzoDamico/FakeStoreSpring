package org.springdemo.progetto.controllers;
import lombok.AllArgsConstructor;
import org.springdemo.progetto.entities.User;
import org.springdemo.progetto.services.AccountingService;
import org.springdemo.progetto.services.KeycloakService;
import org.springdemo.progetto.support.ResponseMessage;
import org.springdemo.progetto.support.UserRegistrationRequest;
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
            User res = req.getUser();

            if (res != null&&controlEl(res) && accountingService.getUserEmail(res.getEmail()).isEmpty()) {
                String cel=res.getPhone().replace("-","").replace(" ","");
                if (!cel.contains("+")) {
                    cel="+39"+cel;
                }
                res.setPhone(cel);
                res = keycloakService.addUser(res, req.getPassword());
                if (res == null) {
                    return new ResponseEntity<>("ERROR_KEYCLOCK", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("FIELD_ARE_INCORECT_OR_MAIL_USER_ALREADY_EXISTS", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (MailUserAlreadyExistsException e) {
            return new ResponseEntity<>(new ResponseMessage("ERROR_MAIL_USER_ALREADY_EXISTS"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<User> getAll() {
        return accountingService.getAllUsers();
    }

    private boolean controlEl(User s) {
        String regexEmail ="^[-A-Za-z0-9._%&$+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        String regexCell = "^([\\+][0-9][0-9])?[0-9][0-9][0-9][-\\s\\.]?[0-9][-\\s\\.]?[0-9][0-9][-\\s\\.]?[0-9][-\\s\\.]?[0-9][0-9][0-9]$";
        String regexCap = "^\\d{5}$";
        return s.getEmail().matches(regexEmail) && s.getCap().matches(regexCap) && s.getPhone().matches(regexCell);
    }
}