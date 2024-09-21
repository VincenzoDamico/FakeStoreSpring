package org.springdemo.progetto.repositories;

import org.springdemo.progetto.entities.Brand;
import org.springdemo.progetto.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query("SELECT DISTINCT b FROM Brand b JOIN b.prodotti p WHERE p.category.name = :catName")
    List<Brand> findByProdCategory(@Param("catName") String catName);
}
