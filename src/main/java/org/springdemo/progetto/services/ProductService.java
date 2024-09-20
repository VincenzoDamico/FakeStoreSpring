package org.springdemo.progetto.services;

import org.springdemo.progetto.entities.Brand;
import org.springdemo.progetto.entities.Category;
import org.springdemo.progetto.entities.Product;
import org.springdemo.progetto.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class ProductService {
    @Autowired
    private ProductRepository productRepository;



    @Transactional(readOnly = true)
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(String catName) {
        return productRepository.findByCategoryName(catName);
    }
    /*
    @Transactional(readOnly = true)
    public List<Product> getProduct(String name) {
        return productRepository.findByName(name);
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(Brand brand) {
        return productRepository.findByBrand(brand);
    }

    @Transactional(readOnly = true)
    public List<Product> getProduct(double priceL,double priceG) {
        return productRepository.findByPriceLessThanEqualAndPriceGreaterThanEqual(priceL,priceG);
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(Category category,String name) {
        return productRepository.findByCategoryAndName( category, name);
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(Category category,Brand brand) {
        return productRepository.findByCategoryAndBrand( category, brand);
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(Category category,double priceL, double priceG) {
        return productRepository.findByCategoryAndPriceLessThanEqualAndPriceGreaterThanEqual( category, priceL,  priceG);
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(double priceL, double priceG,Brand brand) {
        return productRepository. findByPriceLessThanEqualAndPriceGreaterThanEqualAndBrand( priceL,  priceG, brand);
    }

    @Transactional(readOnly = true)
    public List<Product> getProduct(double priceL, double priceG,String name) {
        return productRepository.findByPriceLessThanEqualAndPriceGreaterThanEqualAndName(priceL, priceG, name);
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(Brand brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(Category category,Brand brand, String name) {
        return productRepository.findByCategoryAndBrandAndName( category, brand,  name);
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(Category category,double priceL, double priceG, String name) {
        return productRepository.findByCategoryAndPriceLessThanEqualAndPriceGreaterThanEqualAndName( category, priceL, priceG, name);
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(Category category,double priceL, double priceG,Brand brand){
        return productRepository.findByCategoryAndPriceLessThanEqualAndPriceGreaterThanEqualAndBrand( category, priceL, priceG, brand);
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(double priceL, double priceG,Brand brand,String name){
        return productRepository.findByPriceLessThanEqualAndPriceGreaterThanEqualAndBrandAndName( priceL, priceG, brand,name);
    }
    @Transactional(readOnly = true)
    public List<Product> getProduct(Category category,double priceL, double priceG,Brand brand,String name){
        return productRepository.findByCategoryAndPriceLessThanEqualAndPriceGreaterThanEqualAndBrandAndName(category, priceL, priceG, brand,name);
    }

*/



}
