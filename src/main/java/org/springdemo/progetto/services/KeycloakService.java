
package org.springdemo.progetto.services;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springdemo.progetto.entities.User;
import org.springdemo.progetto.support.exeception.MailUserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class KeycloakService {
    @Autowired
    private AccountingService accountingService;
    private Keycloak keycloak;

//    @Value("${keycloak.realm}")
//    public static String realm;
    public KeycloakService(Keycloak keycloak){
        this.keycloak=keycloak;
    }

    public User addUser(User userToAdd, String password) throws MailUserAlreadyExistsException {
        try {
//definizione dello user
            UserRepresentation user = new UserRepresentation();

            user.setEnabled(true);
            user.setUsername(userToAdd.getName());
            user.setEmail(userToAdd.getEmail());
            user.setEmailVerified(true);
// definizione delle credenziali
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(password);
            List<CredentialRepresentation> list=new ArrayList<>();
            list.add(passwordCred);
            user.setCredentials(list);
            //per avere il token per creare nuove risorse
            RealmResource realmResource = keycloak.realm("fake-store");
            UsersResource usersResource = realmResource.users();
            //end point per creare user
            Response response = usersResource.create(user);
            if(response.getStatus() == Response.Status.CREATED.getStatusCode() ){
                accountingService.registerUser(userToAdd);
                return userToAdd;
            }

/*// Get realm
            RealmResource realmResource = keycloak.realm("fake-store");
            UsersResource usersResource = realmResource.users();
            System.out.println(usersResource.userProfile());
// Create user (requires manage-users role)
            Response response = usersResource.create(user);

            System.out.printf("Response: %s %s%n", response.getStatus(), response.getStatusInfo());
            System.out.println(response.getLocation());
            String userId = CreatedResponseUtil.getCreatedId(response);
            System.out.printf("User created with userId: %s%n", userId);*/



/*
            UserResource userResource = usersResource.get(userId);

// Set password credential
            userResource.resetPassword(passwordCred);


// Get client
            ClientRepresentation app1Client = realmResource.clients().findByClientId("api-store").get(0);

// Get client level role (requires view-clients role)
            RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()).roles().get("user").toRepresentation();

// Assign client level role to user
            userResource.roles().clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));*/


        } catch (MailUserAlreadyExistsException e) { //sono sicuro che non ci sia questa eventualità ma comunque l'integro
            throw new MailUserAlreadyExistsException();
        }
        return null;
    }
}

