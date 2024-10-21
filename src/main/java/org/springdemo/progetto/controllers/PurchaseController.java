package org.springdemo.progetto.controllers;

import org.springdemo.progetto.entities.Order_bucket;
import org.springdemo.progetto.entities.Order_item;
import org.springdemo.progetto.services.OrderBucketService;
import org.springdemo.progetto.support.DTO.Order_ItemDTO;
import org.springdemo.progetto.support.MyConstant;
import org.springdemo.progetto.support.exeception.NullParameterExecption;
import org.springdemo.progetto.support.exeception.ProductNotExistException;
import org.springdemo.progetto.support.exeception.QuantityNonSufficientlyException;
import org.springdemo.progetto.support.exeception.UserNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/ApiPurchase")
public class PurchaseController {

    @Autowired
    private OrderBucketService orderBucketService;
    @PostMapping()
    public ResponseEntity<?> purchase( @AuthenticationPrincipal Jwt jwt, @RequestBody @Valid List<Order_ItemDTO> orderItemList){
        String email=jwt.getClaimAsString("preferred_username");
        try {
            orderBucketService.addOrderItem(orderItemList,email);
        }catch ( ProductNotExistException e){
            return new ResponseEntity<>(MyConstant.ERR_PROD, HttpStatus.BAD_REQUEST);
        }catch (QuantityNonSufficientlyException q){
            return new ResponseEntity<>(MyConstant.ERR_STOCK, HttpStatus.BAD_REQUEST);
        }catch (UserNotExistsException e){
            return new ResponseEntity<>(MyConstant.ERR_USER, HttpStatus.BAD_REQUEST);
        }catch (NullParameterExecption e){
            return new ResponseEntity<> (MyConstant.ERR_PARMAM,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(MyConstant.CAT, HttpStatus.OK);
    }


}
