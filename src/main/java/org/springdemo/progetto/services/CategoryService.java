package org.springdemo.progetto.services;

import org.springdemo.progetto.entities.Category;
import org.springdemo.progetto.repositories.CategoryRepository;
import org.springdemo.progetto.support.exeception.MailUserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public List<Category> getAllCat() {
        return categoryRepository.findAll();
    }


}