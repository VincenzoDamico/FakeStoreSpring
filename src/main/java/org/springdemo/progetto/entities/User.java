package org.springdemo.progetto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int customer_id;

    @Basic
    @Column(name = "name", nullable = true, length = 50)
    private String name;
    @Basic
    @Column(name = "surname", nullable = true, length = 50)
    private String surname;

    @Basic
    @Column(name = "email", nullable = false, length = 90,unique = true)
    private String email;

    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    private String phone;
    @Basic
    @Column(name = "address", nullable = true, length = 150)
    private String address;
    @Basic
    @Column(name = "city", nullable = true, length = 15)
    private String city;
    @Basic
    @Column(name = "cap", nullable = true, length = 5)
    private String cap;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,targetEntity = Order_bucket.class, mappedBy = "user", cascade = CascadeType.ALL)
 //  @JoinColumn(name ="user")
    List<Order_bucket> purchases;
}
