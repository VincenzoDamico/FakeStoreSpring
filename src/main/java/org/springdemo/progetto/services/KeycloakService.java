package org.springdemo.progetto.services;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;

import org.keycloak.admin.client.Keycloak;

import org.keycloak.admin.client.resource.RealmResource;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springdemo.progetto.entities.User;
import org.springdemo.progetto.support.exeception.FieldIncorrectException;
import org.springdemo.progetto.support.exeception.MailUserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

import java.util.List;


@Service
@Slf4j
public class KeycloakService {
    @Autowired
    private AccountingService accountingService;
    private Keycloak keycloak;

    //    @Value("${keycloak.realm}")
//    public static String realm;
    public KeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public User addUser(User userToAdd, String password) {
        if (accountingService.getUserEmail(userToAdd.getEmail()).isEmpty()) {
            if(controlEl(userToAdd)) {
                String cel = userToAdd.getPhone().replace("-", "").replace(" ", "");
                if (!cel.contains("+")) {
                    cel = "+39" + cel;
                }
                userToAdd.setPhone(cel);
            }else{
                throw new FieldIncorrectException();
            }
        }else{
            throw new MailUserAlreadyExistsException();
        }
        try {
//definizione dello user
            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(userToAdd.getEmail());
            user.setEmail(userToAdd.getEmail());
            user.setEmailVerified(true);
// definizione delle credenziali
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(password);
            List<CredentialRepresentation> list = new ArrayList<>();
            list.add(passwordCred);
            user.setCredentials(list);
            //per avere il token per creare nuove risorse
            RealmResource realmResource = keycloak.realm("fake-store");
            UsersResource usersResource = realmResource.users();
            //end point per creare user
            Response response = usersResource.create(user);
            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                User s=accountingService.registerUser(userToAdd);
                return s;
            }


        } catch (MailUserAlreadyExistsException e) { //sono sicuro che non ci sia questa eventualità ma comunque l'integro
            throw new MailUserAlreadyExistsException();
        }
        return null;
    }
    private boolean controlEl(User s) {
        String regexEmail ="^[-A-Za-z0-9._%&$+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        String regexCell = "^([\\+][0-9][0-9])?[0-9][0-9][0-9][-\\s\\.]?[0-9][-\\s\\.]?[0-9][0-9][-\\s\\.]?[0-9][-\\s\\.]?[0-9][0-9][0-9]$";
        String regexCap = "^\\d{5}$";
        return s.getEmail().matches(regexEmail) && s.getCap().matches(regexCap) && s.getPhone().matches(regexCell);
    }
}

