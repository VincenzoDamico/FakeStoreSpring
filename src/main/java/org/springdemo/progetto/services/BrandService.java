package org.springdemo.progetto.services;

import org.springdemo.progetto.entities.Brand;
import org.springdemo.progetto.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service

public class BrandService {
    @Autowired
    private BrandRepository brandRapository;
    @Transactional(readOnly = true)
    public List<Brand> getBrandCat(String catName) {
        return brandRapository.findByProdCategory(catName);
    }
}
