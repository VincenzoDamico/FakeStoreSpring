package org.springdemo.progetto.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
@Data

@Table(name="order_bucket")
@Entity
public class Order_bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private int order_id;

    @Basic
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Date order_date;

    @Basic
    @Column(name = "total_price", nullable = true)
    private double total_price;

    @OneToMany(targetEntity = Order_item.class,mappedBy = "order_bucket", cascade = CascadeType.MERGE)
    private List<Order_item> order_bucket;

    @ManyToOne
    @JoinColumn(name = "users")
    private User user;

}
