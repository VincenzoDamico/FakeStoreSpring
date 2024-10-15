package org.springdemo.progetto.services;

import org.springdemo.progetto.entities.Brand;
import org.springdemo.progetto.entities.Category;
import org.springdemo.progetto.entities.Product;
import org.springdemo.progetto.repositories.ProductRepository;
import org.springdemo.progetto.support.exeception.ProductNotExistException;
import org.springdemo.progetto.support.exeception.QuantityNonSufficientlyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Transactional(readOnly = true)
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Product> getProductCategory(String catname) {
        return productRepository.findByCategoryName(catname);
    }

    @Transactional(readOnly = true)
    public List<Product> getProductCategoryBrands(String catname,List<String> brands) {
        return productRepository.findByCategoryNameAndByBrandIn(catname,brands);
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Product updateQuantity(int quant, Product p) throws ProductNotExistException, QuantityNonSufficientlyException {
         Product prod=productRepository.findById(p.getId());
        if (prod == null || !prod.equals(p)) {
            throw new ProductNotExistException();
        }
        if(prod.getStock_quantity()<quant) {
            throw new QuantityNonSufficientlyException();
        }

        int newQ=prod.getStock_quantity()-quant;
        prod.setStock_quantity(newQ);
        return prod;
    }

}
