package org.springdemo.progetto.controllers;

import org.springdemo.progetto.entities.Product;
import org.springdemo.progetto.services.BrandService;
import org.springdemo.progetto.services.CategoryService;
import org.springdemo.progetto.services.ProductService;

import org.springdemo.progetto.support.MyConstant;
import org.springdemo.progetto.support.ResponseMessage;
import org.springdemo.progetto.support.exeception.BrandInesistenteException;
import org.springdemo.progetto.support.exeception.CategoryInesistenteException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;


@RestController
@RequestMapping("/ApiProduct")

public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/productAll")
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/productCategory")
    public ResponseEntity<?> getProductCategory(   @RequestParam(name = "catname")String catname) {
        try {
            List<Product> result = productService.getProductCategory(catname);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (CategoryInesistenteException c){
            return new ResponseEntity<>(new ResponseMessage(MyConstant.ERR_CAT), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/productCategoryBrand")
    public ResponseEntity<?> getProductCategoryBrand(   @RequestParam(name = "catname")String catname, @RequestParam(name = "brands") List<String> brands) {
        try {
            List<Product> result = productService.getProductCategoryBrands(catname, brands);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch(CategoryInesistenteException c){
            return new ResponseEntity<>(new ResponseMessage(MyConstant.ERR_CAT), HttpStatus.BAD_REQUEST);
        }catch(BrandInesistenteException b){
            return new ResponseEntity<>(new ResponseMessage(MyConstant.ERR_BRAND), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/AddProduct")
    public ResponseEntity<?> addProd(@RequestBody Product p){
        try {
            productService.addProduct(p);
            return new ResponseEntity<>("Product_added",HttpStatus.OK);

        }catch (CategoryInesistenteException e){
            return new ResponseEntity<> (MyConstant.ERR_CAT,HttpStatus.BAD_REQUEST);
        }catch (BrandInesistenteException e){
            return new ResponseEntity<> (MyConstant.ERR_BRAND,HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/UpdateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody Product p){
        try {
            productService.updateProduct(p);
            return new ResponseEntity<>("Product_added",HttpStatus.OK);

        }catch (CategoryInesistenteException e){
            return new ResponseEntity<> (MyConstant.ERR_CAT,HttpStatus.BAD_REQUEST);
        }catch (BrandInesistenteException e){
            return new ResponseEntity<> (MyConstant.ERR_BRAND,HttpStatus.BAD_REQUEST);
        }
    }
}