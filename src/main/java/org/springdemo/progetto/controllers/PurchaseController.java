package org.springdemo.progetto.controllers;

import org.springdemo.progetto.configurations.keyclock.KeyCloackJwtAuthenticationConverter;
import org.springdemo.progetto.entities.Order_bucket;
import org.springdemo.progetto.entities.Order_item;
import org.springdemo.progetto.entities.User;
import org.springdemo.progetto.services.AccountingService;
import org.springdemo.progetto.services.OrderBucketService;
import org.springdemo.progetto.support.exeception.ProductNotExistException;
import org.springdemo.progetto.support.exeception.QuantityNonSufficientlyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/ApiPurchase")
public class PurchaseController {
    @Autowired
    private AccountingService accountingService;
    @Autowired
    private OrderBucketService orderBucketService;
    @PostMapping()
    public ResponseEntity<?> purchase(@NotNull @AuthenticationPrincipal Jwt jwt,@NotNull @RequestBody List< Order_item> orderItemList){
        //TO-DO verificare che i prodotti esistano e la uquantità richiesta sia coretta


        String email=jwt.getClaimAsString("preferred_username");

        System.out.println( orderItemList);

        List<User> lUser=accountingService.getUserEmail(email);
        if(lUser.isEmpty()) {
            return new ResponseEntity<>("USER_NOT_EXISTS", HttpStatus.BAD_REQUEST);
        }
        User s=lUser.getFirst();


        Order_bucket orderBucket = new Order_bucket();
        orderBucket.setUser(s);
        orderBucket.setOrder_bucket(orderItemList);
        try {
            orderBucket=orderBucketService.addOrderItem(orderBucket);
            s.getPurchases().add(orderBucket);
        }catch ( ProductNotExistException e){
            return new ResponseEntity<>("PRODUCT_NOT_EXISTS", HttpStatus.BAD_REQUEST);
        }catch (QuantityNonSufficientlyException q){
            return new ResponseEntity<>("STOCK_SHORTAGE", HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>("CART_UPDATED", HttpStatus.OK);
    }


}
