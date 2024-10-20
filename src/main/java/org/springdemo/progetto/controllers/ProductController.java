package org.springdemo.progetto.controllers;

import org.springdemo.progetto.entities.Product;
import org.springdemo.progetto.services.BrandService;
import org.springdemo.progetto.services.CategoryService;
import org.springdemo.progetto.services.ProductService;

import org.springdemo.progetto.support.DTO.ProductDTO;
import org.springdemo.progetto.support.MyConstant;
import org.springdemo.progetto.support.ResponseMessage;
import org.springdemo.progetto.support.exeception.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;


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
        }catch (NullParameterExecption e){
            return new ResponseEntity<> (MyConstant.ERR_PARMAM,HttpStatus.BAD_REQUEST);
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
        }catch ( NullParameterExecption e){
            return new ResponseEntity<> (MyConstant.ERR_PARMAM,HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('shop')")
    @PostMapping("/AddProduct")
    public ResponseEntity<?> addProd(@RequestBody @Valid @NotNull ProductDTO p){
        try {
            productService.addProduct(p);
            return new ResponseEntity<>("Product_added",HttpStatus.OK);

        }catch (CategoryInesistenteException e){
            return new ResponseEntity<> (MyConstant.ERR_CAT,HttpStatus.BAD_REQUEST);
        }catch (BrandInesistenteException e){
            return new ResponseEntity<> (MyConstant.ERR_BRAND,HttpStatus.BAD_REQUEST);
        }catch (QuantityNonSufficientlyException q){
            return new ResponseEntity<> (MyConstant.ERR_STOCK,HttpStatus.BAD_REQUEST);
        }catch (NullParameterExecption e){
            return new ResponseEntity<> (MyConstant.ERR_PARMAM,HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('shop')")
    @PostMapping("/UpdateProduct")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductDTO p){
        try {
            productService.updateProduct(p);
            return new ResponseEntity<>("Product_added",HttpStatus.OK);

        }catch (CategoryInesistenteException e){
            return new ResponseEntity<> (MyConstant.ERR_CAT,HttpStatus.BAD_REQUEST);
        }catch (BrandInesistenteException e){
            return new ResponseEntity<> (MyConstant.ERR_BRAND,HttpStatus.BAD_REQUEST);
        }catch (QuantityNonSufficientlyException q){
            return new ResponseEntity<> (MyConstant.ERR_STOCK,HttpStatus.BAD_REQUEST);
        }catch (NullParameterExecption e){
            return new ResponseEntity<> (MyConstant.ERR_PARMAM,HttpStatus.BAD_REQUEST);
        }
    }
}