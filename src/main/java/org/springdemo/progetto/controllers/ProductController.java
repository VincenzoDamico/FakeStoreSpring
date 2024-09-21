package org.springdemo.progetto.controllers;

import org.springdemo.progetto.entities.Product;
import org.springdemo.progetto.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;

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

       for(Product p: productService.getAllProduct()){
           System.out.println(p);
       }
        return productService.getAllProduct();
    }

    @GetMapping("/productCategory")
    public List<Product> getProductCategory(   @RequestParam(name = "catname")String catname) {
        //TO-DO verificare che la categoeia esista


        List<Product> result =  productService.getProductCategory(catname);
        if (!result.isEmpty()) {
            for (Product p : result) {
                System.out.println(p.getName());
            }
        }else {
            System.out.println("è vuota coglione");
        }
        return result;
    }
    @GetMapping("/productCategoryBrand")
    public List<Product> getProductCategoryBrand(   @RequestParam(name = "catname")String catname, @RequestParam(name = "brands") List<String> brands) {
        //TO-DO verificare che la categoria e brand esistano

        List<Product> result = productService.getProductCategoryBrands(catname, brands);
        if (!result.isEmpty()) {
            for (Product p : result) {
                System.out.println(p.getName());
            }
        }else {
            System.out.println("è vuota coglione");
        }
        return result;
    }

}