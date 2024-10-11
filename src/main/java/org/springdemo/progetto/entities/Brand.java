package org.springdemo.progetto.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
//Table(name="Brand")
@Entity
public class Brand {
    @Id
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic
    @Column(name = "email", nullable = true, length = 90)
    private String email;
    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    private String phone;
    @Basic
    @Column(name = "address", nullable = true, length = 150)
    private String address;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,targetEntity = Product.class, mappedBy = "brand", cascade = CascadeType.ALL)
    private Set<Product> prodotti;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        return name.equals(brand.name);
    }
}
