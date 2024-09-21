package org.springdemo.progetto.controllers;

import org.springdemo.progetto.entities.Brand;

import org.springdemo.progetto.services.BrandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/ApiBrand")

public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/brandCategory")
    public List<Brand> getProductCategory(@RequestParam(name = "category")String category) {
        //TO-DO verificare che la categoeia esista

        List<Brand> result =  brandService.getBrandCat(category);
        if (!result.isEmpty()) {
            for (Brand b : result) {
                System.out.println(b.getName());
            }
        }else {
            System.out.println("è vuota coglione");
        }
        return result;
    }
}
