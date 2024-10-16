package org.springdemo.progetto.services;

import org.springdemo.progetto.entities.Order_bucket;
import org.springdemo.progetto.entities.Order_item;
import org.springdemo.progetto.entities.User;
import org.springdemo.progetto.repositories.OrderBucketRepository;
import org.springdemo.progetto.support.exeception.ProductNotExistException;
import org.springdemo.progetto.support.exeception.QuantityNonSufficientlyException;
import org.springdemo.progetto.support.exeception.UserNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class OrderBucketService {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private AccountingService accountingService;
    @Autowired
    private OrderBucketRepository orderBucketRepository;
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void addOrderItem(List<Order_item> orderItemList, String email)  {
        try {

            List<User> lUser=accountingService.getUserEmail(email);
            if(lUser.isEmpty()) {
               throw new UserNotExistsException();
            }
            User s=lUser.getFirst();
            Order_bucket orderBucket = new Order_bucket();
            orderBucket.setUser(s);

            List<Order_item> l=new LinkedList<>();
            for (Order_item or : orderItemList) {
                or.setOrder_bucket(orderBucket);
                l.add(orderItemService.addOrderItem(or));
            }
            orderBucket.setOrder_bucket(l);
            double total_price = getTotalPrice(l);
            orderBucket.setTotal_price(total_price);

           orderBucketRepository.save(orderBucket);
            s.getPurchases().add(orderBucket);

        } catch (QuantityNonSufficientlyException | ProductNotExistException e) {
            throw e;
        }
    }
    private double getTotalPrice(List<Order_item> orderItemList) {
        double res=0;
        for(Order_item oi:orderItemList ){
            res+=oi.getProduct().getPrice()*oi.getQuantity();
        }
        return  res;
    }
}
