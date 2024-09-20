package org.springdemo.progetto.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data

@Entity
public class Order_item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    private int order_item_id;
    @Basic
    @Column(name = "quantity", nullable = true)
    private int quantity;
    @Basic
    @Column(name = "unit_price", nullable = true)
    private double unit_price;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @ManyToOne
    @JoinColumn(name="order_bucket")
    private Order_bucket order_bucket;


}
