package org.springdemo.progetto.repositories;

import org.springdemo.progetto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByName(String name);
    List<User> findBySurname (String surname);
    List<User> findByNameAndSurname (String firstName, String lastName);
    List<User> findByEmail(String email);
    User findById(int id);
    boolean existsByEmail(String email);


}
