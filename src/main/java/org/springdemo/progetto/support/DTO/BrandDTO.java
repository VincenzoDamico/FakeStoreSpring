package org.springdemo.progetto.support.DTO;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Data
public class BrandDTO {
    @NotNull
    private String name;

    private String email;

    private String phone;

    private String address;
}
