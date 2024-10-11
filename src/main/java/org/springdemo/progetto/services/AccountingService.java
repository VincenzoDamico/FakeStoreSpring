package org.springdemo.progetto.services;

import org.springdemo.progetto.entities.User;
import org.springdemo.progetto.repositories.UserRepository;
import org.springdemo.progetto.support.exeception.MailUserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountingService {
    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User registerUser(User user) throws MailUserAlreadyExistsException {
        if ( userRepository.existsByEmail(user.getEmail()) ) {
            throw new MailUserAlreadyExistsException();
        }


        return userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public List<User> getUserEmail(String email ){
        System.out.println(userRepository.findByEmail(email));
        return userRepository.findByEmail(email);
    }
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}