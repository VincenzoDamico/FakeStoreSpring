package org.springdemo.progetto.services;

import org.springdemo.progetto.entities.User;
import org.springdemo.progetto.repositories.UserRepository;
import org.springdemo.progetto.support.exeception.FieldUserIncorrectException;
import org.springdemo.progetto.support.exeception.MailUserAlreadyExistsException;
import org.springdemo.progetto.support.exeception.NullParameterExecption;
import org.springdemo.progetto.support.exeception.UserNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountingService {
    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User registerUser(User user) throws MailUserAlreadyExistsException {
        if ( userRepository.existsByEmail(user.getEmail()) ) {
            throw new MailUserAlreadyExistsException();
        }
        User s=new User();//si autogenera l'id
        //sono sicuro che non ho problemi con l'id nella base di dati
        s.setCap(user.getCap());
        s.setCity(user.getCity());
        s.setSurname(user.getSurname());
        s.setEmail(user.getEmail());
        s.setPhone(user.getPhone());
        s.setName(user.getName());
        s.setAddress(user.getAddress());
        s.setPurchases(new ArrayList<>());
        return userRepository.save( s);
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void modAddressUser(String email ,String cap , String address,String city){
        if (email==null||cap==null || address==null || city==null){
            throw new NullParameterExecption();
        }
        if ( !userRepository.existsByEmail(email) ) {
            throw new UserNotExistsException();
        }
        if (!cap.matches("^\\d{5}$")){
            throw new FieldUserIncorrectException();
        }
        User s=userRepository.findByEmail(email).getFirst();
        s.setCap(cap);
        s.setCity(city);
        s.setAddress(address);
    }

    @Transactional(readOnly = true)
    public List<User> getUserEmail(String email ){
        return userRepository.findByEmail(email);
    }
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}