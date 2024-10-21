package org.springdemo.progetto.support;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springdemo.progetto.entities.User;
import org.springdemo.progetto.support.DTO.UserDTO;

import javax.validation.Valid;
@Setter
@Getter
@Data
public class UserRegistrationRequest {
    // Getters and Setters
    @Valid
    private UserDTO user;

    @NotBlank(message = "Password is mandatory")
    private String password;

    public UserRegistrationRequest(UserDTO user, String password) {
        this.user = user;
        this.password = password;
    }

}


