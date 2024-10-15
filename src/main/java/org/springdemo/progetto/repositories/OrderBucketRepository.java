package org.springdemo.progetto.repositories;

import org.springdemo.progetto.entities.Order_bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBucketRepository extends JpaRepository<Order_bucket, Integer> {
}
