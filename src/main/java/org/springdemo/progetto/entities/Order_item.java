package org.springdemo.progetto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="order_bucket")
    private Order_bucket order_bucket;


}
