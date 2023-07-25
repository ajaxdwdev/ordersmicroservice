package com.alex.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alex.orders.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

}
