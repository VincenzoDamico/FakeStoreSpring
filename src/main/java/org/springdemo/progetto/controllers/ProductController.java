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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @GetMapping("/productAll")
    public List<Product> getAllProduct() {

       for(Product p: productService.getAllProduct()){
           System.out.println(p);
       }
        return productService.getAllProduct();
    }

    @GetMapping("/productCategory")
    public ResponseEntity<?> getProductCategory(   @RequestParam(name = "catname")String catname) {
        if (categoryService.getCatName(catname).isEmpty()){
            return new ResponseEntity<>(new ResponseMessage(MyConstant.ERR_CAT), HttpStatus.BAD_REQUEST);
        }
        List<Product> result =  productService.getProductCategory(catname);
        return  new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/productCategoryBrand")
    public ResponseEntity<?> getProductCategoryBrand(   @RequestParam(name = "catname")String catname, @RequestParam(name = "brands") List<String> brands) {
        if (categoryService.getCatName(catname).isEmpty() && controll(brands)) {
            return new ResponseEntity<>(new ResponseMessage(MyConstant.ERR_CAT+"_OR_"+MyConstant.ERR_BRAND), HttpStatus.BAD_REQUEST);
        }
        List<Product> result = productService.getProductCategoryBrands(catname, brands);
        return new ResponseEntity<>(result, HttpStatus.OK);
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

    private boolean controll(List<String> brands) {
        for (String b:brands){
            if (brandService.getBrandName(b).isEmpty()){
                return false;
            }
        }
        return  true;
    }
}