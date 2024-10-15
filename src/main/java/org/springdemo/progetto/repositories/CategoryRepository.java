package org.springdemo.progetto.repositories;

import org.springdemo.progetto.entities.Category;
import org.springdemo.progetto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByName(String name);

}
