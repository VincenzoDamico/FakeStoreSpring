package org.springdemo.progetto.controllers;

import org.springdemo.progetto.entities.Brand;

import org.springdemo.progetto.services.BrandService;

import org.springdemo.progetto.services.CategoryService;
import org.springdemo.progetto.support.MyConstant;
import org.springdemo.progetto.support.ResponseMessage;
import org.springdemo.progetto.support.exeception.CategoryInesistenteException;
import org.springdemo.progetto.support.exeception.NullParameterExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getProductCategory(@RequestParam(name = "category")String category) {
        try {
            List<Brand> result =  brandService.getBrandCat(category);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (CategoryInesistenteException c){
            return new ResponseEntity<>(new ResponseMessage(MyConstant.ERR_CAT), HttpStatus.BAD_REQUEST);
        }catch (NullParameterExecption e){
            return new ResponseEntity<> (MyConstant.ERR_PARMAM,HttpStatus.BAD_REQUEST);
        }
    }
}
