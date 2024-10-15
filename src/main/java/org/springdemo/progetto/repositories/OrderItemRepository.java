package org.springdemo.progetto.repositories;

import org.springdemo.progetto.entities.Order_item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<Order_item, Integer> {
}