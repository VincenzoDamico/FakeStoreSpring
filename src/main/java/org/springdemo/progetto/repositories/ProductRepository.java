package org.springdemo.progetto.repositories;

import org.springdemo.progetto.entities.Brand;
import org.springdemo.progetto.entities.Category;
import org.springdemo.progetto.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Integer> {
    //implemento le query che sto facendo come se fossero filtri
    // do priorita categoria1 prezzo2 brand3 nome 4
    //1 elemento
    List<Product> findByName(String name);
    List<Product> findByBrand(Brand brand);
    @Query("select p from Product p where p.category.name = :catname")
    List<Product> findByCategoryName(@Param("catname") String catname);

    @Query("select p from Product p where p.category.name = :catname and p.brand.name  in :brands ")
    List<Product> findByCategoryNameAndByBrandIn( @Param("catname")String category,  @Param("brands")List<String> brands);



    List<Product> findByCategory(Category category);
    List<Product> findByPriceLessThanEqualAndPriceGreaterThanEqual(double priceL,double priceG);
    //2 elementi
    List<Product> findByCategoryAndBrand(Category category,Brand brand);

    List<Product> findByCategoryAndName(Category category,String name);
    List<Product> findByCategoryAndPriceLessThanEqualAndPriceGreaterThanEqual(Category category,double priceL, double priceG);
    List<Product> findByPriceLessThanEqualAndPriceGreaterThanEqualAndBrand(double priceL, double priceG,Brand brand);
    List<Product> findByPriceLessThanEqualAndPriceGreaterThanEqualAndName(double priceL, double priceG,String name);
    List<Product> findByBrandAndName(Brand brand,String name);



    //3 elementi
    List<Product> findByCategoryAndBrandAndName(Category category,Brand brand, String name);

    List<Product> findByCategoryAndPriceLessThanEqualAndPriceGreaterThanEqualAndName(Category category, double priceL, double priceG, String name);

    List<Product> findByCategoryAndPriceLessThanEqualAndPriceGreaterThanEqualAndBrand(Category category, double priceL, double priceG, Brand brand);

    List<Product> findByPriceLessThanEqualAndPriceGreaterThanEqualAndBrandAndName( double priceL, double priceG, Brand brand,String name);
    //4 el
    List<Product> findByCategoryAndPriceLessThanEqualAndPriceGreaterThanEqualAndBrandAndName(Category category, double priceL, double priceG, Brand brand,String name);

}
