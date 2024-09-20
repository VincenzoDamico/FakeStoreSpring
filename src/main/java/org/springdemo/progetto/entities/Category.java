package org.springdemo.progetto.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.Set;

@Data

@Table(name="category")
@Entity
public class Category {

    @Id
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = 250)
    private String description;

  /*  @OneToMany(targetEntity = Product.class, mappedBy = "category",cascade=CascadeType.ALL)
 //   @JoinColumn(name="category")
    private Set<Product> prodottiC;*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return name.equals(category.name);
    }

}
