package org.springdemo.progetto.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @Basic
    @Column(name = "size", nullable = false)
    private Integer size;
    @Basic
    @Column(name = "color", nullable = true, length = 50)
    private String color;

    @Basic
    @Column(name = "description", nullable = true, length = 250)
    private String description;

    @Basic
    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Basic
    @Column(name = "stock_quantity", nullable = true)
    private int stock_quantity;
    @Basic
    @Column(name = "price", nullable = true)
    private double price;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand")
    private Brand brand;

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Order_item.class, mappedBy = "product", cascade = CascadeType.ALL)
    //@JoinColumn(name = "product")
    private List<Order_item> listOrder;


}
