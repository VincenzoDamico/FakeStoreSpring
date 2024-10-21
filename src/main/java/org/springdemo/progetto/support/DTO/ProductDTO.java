package org.springdemo.progetto.support.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springdemo.progetto.entities.Brand;
import org.springdemo.progetto.entities.Category;
import org.springdemo.progetto.entities.Product;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Data
public class ProductDTO {
    @NotBlank
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private Integer size;

    @NotBlank
    private String color;


    private String description;


    private String image_path;

    @NotBlank
    private int stock_quantity;
    @NotBlank
    private double price;
    @Valid
    private CategoryDTO category;
    @Valid
    private BrandDTO brand;
}
