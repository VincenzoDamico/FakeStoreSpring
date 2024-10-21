package org.springdemo.progetto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@NotNull
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Basic
    @Column(name = "size", nullable = false)
    private Integer size;
    @Basic
    @Column(name = "color", nullable = false, length = 50)
    private String color;

    @Basic
    @Column(name = "description", nullable = true, length = 250)
    private String description;

    @Basic
    @Column(name = "image_path", nullable = false)
    private String image_path;

    @Basic
    @Column(name = "stock_quantity", nullable = false)
    private int stock_quantity;
    @Basic
    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand")
    private Brand brand;

//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY,targetEntity = Order_item.class, mappedBy = "product", cascade = CascadeType.ALL)
//    //@JoinColumn(name = "product")
//    private List<Order_item> listOrder;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (!name.equals(product.name)) return false;
        if (!size.equals(product.size)) return false;
        if (!color.equals(product.color)) return false;
        if (!category.equals(product.category)) return false;
        return brand.equals(product.brand);
    }

}
