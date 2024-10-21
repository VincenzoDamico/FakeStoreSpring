package org.springdemo.progetto.support.DTO;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Data
public class CategoryDTO{
    @NotNull
    private String name;

    private String description;

}
