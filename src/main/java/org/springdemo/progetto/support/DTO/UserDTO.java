package org.springdemo.progetto.support.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Data
public class UserDTO {
    private int customer_id;
    @NotNull
    private String name;
    private String surname;
    @NotNull
    private String email;
    private String phone;
    private String address;
    private String city;
    private String cap;
}
