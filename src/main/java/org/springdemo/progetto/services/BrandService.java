package org.springdemo.progetto.services;

import org.springdemo.progetto.entities.Brand;
import org.springdemo.progetto.repositories.BrandRepository;
import org.springdemo.progetto.repositories.CategoryRepository;
import org.springdemo.progetto.support.exeception.BrandInesistenteException;
import org.springdemo.progetto.support.exeception.CategoryInesistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service

public class BrandService {
    @Autowired
    private BrandRepository brandRapository;
    @Autowired
    private CategoryService categoryService;
    @Transactional(readOnly = true)
    public List<Brand> getBrandCat(String catName) {
        if (!categoryService.getCatName(catName).isEmpty()) {
            return brandRapository.findByProdCategory(catName);
        }else {
            throw new CategoryInesistenteException();
        }
    }
    @Transactional(readOnly = true)
    public List<Brand> getBrandName(String name){
        List<Brand> res= brandRapository.findByName(name);
        if (res.isEmpty()){
            throw new BrandInesistenteException();
        }
        return res;
    }
}
