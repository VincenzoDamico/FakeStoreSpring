package org.springdemo.progetto.services;

import org.springdemo.progetto.entities.Order_bucket;
import org.springdemo.progetto.entities.Order_item;
import org.springdemo.progetto.repositories.OrderBucketRepository;
import org.springdemo.progetto.repositories.OrderItemRepository;
import org.springdemo.progetto.support.exeception.ProductNotExistException;
import org.springdemo.progetto.support.exeception.QuantityNonSufficientlyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderBucketService {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderBucketRepository orderBucketRepository;
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Order_bucket addOrderItem(Order_bucket orb) throws ProductNotExistException,QuantityNonSufficientlyException {
        try {
            List<Order_item> l=new LinkedList<>();
            for (Order_item or : orb.getOrder_bucket()) {
                or.setOrder_bucket(orb);
                l.add(orderItemService.addOrderItem(or));
            }
            orb.setOrder_bucket(l);
            double total_price = getTotalPrice(l);
            orb.setTotal_price(total_price);
            return orderBucketRepository.save(orb);

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
