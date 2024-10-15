package org.springdemo.progetto.services;

import org.springdemo.progetto.entities.Order_item;
import org.springdemo.progetto.entities.Product;
import org.springdemo.progetto.repositories.OrderItemRepository;
import org.springdemo.progetto.support.exeception.ProductNotExistException;
import org.springdemo.progetto.support.exeception.QuantityNonSufficientlyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductService productService;
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Order_item  addOrderItem(Order_item or) throws ProductNotExistException,QuantityNonSufficientlyException {

        try {
           Product p=productService.updateQuantity(or.getQuantity(), or.getProduct());
           // lo faccio in modo che non abbia problemi con gli id che vengono generati al momento della creazione dell'oggetto
           Order_item res=new Order_item();
           res.setQuantity(or.getQuantity());
           res.setOrder_bucket(or.getOrder_bucket());
           res.setProduct(p);

           orderItemRepository.save(res);
           return  res;
        }catch (ProductNotExistException | QuantityNonSufficientlyException e){
            throw e;
        }
    }
}
