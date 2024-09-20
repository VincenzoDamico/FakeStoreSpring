package org.springdemo.progetto.controllers;

import org.springdemo.progetto.entities.Category;
import org.springdemo.progetto.entities.Product;
import org.springdemo.progetto.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;


@RestController
@RequestMapping()

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
    public List<Product> getProductCategory(   @RequestParam(name = "category")String c) {
        //TO-DO verificare che la categoeia esista

        StringTokenizer s=new StringTokenizer(c.substring(8),"\"");
        String name=s.nextToken();
        System.out.println(name);
        List<Product> result =  productService.getProduct(name);
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