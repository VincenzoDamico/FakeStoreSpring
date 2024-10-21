package org.springdemo.progetto.services;

import org.springdemo.progetto.entities.Brand;
import org.springdemo.progetto.entities.Category;
import org.springdemo.progetto.entities.Product;
import org.springdemo.progetto.repositories.BrandRepository;
import org.springdemo.progetto.repositories.CategoryRepository;
import org.springdemo.progetto.repositories.ProductRepository;
import org.springdemo.progetto.support.DTO.ProductDTO;
import org.springdemo.progetto.support.MyConstant;
import org.springdemo.progetto.support.ResponseMessage;
import org.springdemo.progetto.support.exeception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;

    @Transactional(readOnly = true)
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> getProductCategory(String catname) {
        if (catname == null)
            throw new NullParameterExecption();
        if (categoryService.getCatName(catname).isEmpty()) {
            throw new CategoryInesistenteException();
        }
        return productRepository.findByCategoryName(catname);
    }

    @Transactional(readOnly = true)
    public List<Product> getProductCategoryBrands(String catname, List<String> brands) {
        if (catname == null || brands == null)
            throw new NullParameterExecption();
        if (categoryService.getCatName(catname).isEmpty()) {
            throw new CategoryInesistenteException();
        }
        if (!controll(brands)) {
            throw new BrandInesistenteException();
        }
        return productRepository.findByCategoryNameAndByBrandIn(catname, brands);
    }

    private boolean controll(List<String> brands) {
        for (String b : brands) {
            if (brandService.getBrandName(b).isEmpty()) {
                return false;
            }
        }
        return true;
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Product updateQuantity(int quant, ProductDTO p) {
        Product prod = productRepository.findById(p.getId());
        if (prod == null || !prod.getName().equals(p.getName()) && prod.getSize() != p.getSize() && !prod.getColor().equals(p.getColor()) && !prod.getBrand().getName().equals(p.getBrand().getName())) {
            throw new ProductNotExistException();
        }
        if (prod.getStock_quantity() < quant && quant > 0) {
            throw new QuantityNonSufficientlyException();
        }

        int newQ = prod.getStock_quantity() - quant;
        prod.setStock_quantity(newQ);
        return prod;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateProduct(ProductDTO p) {
        if (p == null)
            throw new NullParameterExecption();
        Product prod = productRepository.findById(p.getId());
        if (prod != null) {
            Category c = categoryService.getCatName(p.getCategory().getName()).getFirst();
            Brand b = brandService.getBrandName(p.getBrand().getName()).getFirst();
            if (c == null) {
                throw new CategoryInesistenteException();
            }
            if (b == null) {
                throw new BrandInesistenteException();
            }
            if (p.getStock_quantity() <= 0) {
                throw new QuantityNonSufficientlyException();
            }
            c.getProdottiC().remove(prod);
            b.getProdotti().remove(prod);

            prod.setStock_quantity(p.getStock_quantity());
            prod.setName(p.getName());
            prod.setBrand(b);
            prod.setCategory(c);
            prod.setColor(p.getColor());
            prod.setSize(p.getSize());
            prod.setPrice(p.getPrice());
            prod.setImage_path(p.getImage_path());
            prod.setDescription(p.getDescription());
            c.getProdottiC().add(prod);
            b.getProdotti().add(prod);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addProduct(ProductDTO p) {
        if (p == null) {
            throw new NullParameterExecption();
        }
        Product prod = productRepository.findById(p.getId());
        if (prod == null) {
            Category c = categoryService.getCatName(p.getCategory().getName()).getFirst();
            Brand b = brandService.getBrandName(p.getBrand().getName()).getFirst();

            if (c == null) {
                throw new CategoryInesistenteException();
            }
            if (b == null) {
                throw new BrandInesistenteException();
            }
            if (p.getStock_quantity() <= 0) {
                throw new QuantityNonSufficientlyException();
            }
            Product res = productRepository.findByCategoryAndBrandAndNameAndPriceAndColorAndSize(c, b, p.getName(), p.getPrice(), p.getColor(), p.getSize()).getFirst();
            if (res == null) {
                Product newprod = new Product();
                newprod.setStock_quantity(p.getStock_quantity());
                newprod.setName(p.getName());
                newprod.setBrand(b);
                newprod.setCategory(c);
                newprod.setColor(p.getColor());
                newprod.setSize(p.getSize());
                newprod.setPrice(p.getPrice());
                newprod.setImage_path(p.getImage_path());
                newprod.setDescription(p.getDescription());
                productRepository.save(newprod);
                c.getProdottiC().add(newprod);
                b.getProdotti().add(newprod);
            } else {
                int q = p.getStock_quantity() + res.getStock_quantity();
                res.setStock_quantity(q);
            }
        } else {
            if (prod.equals(p)) {
                int q = p.getStock_quantity() + prod.getStock_quantity();
                prod.setStock_quantity(q);
            }
        }
    }
}
