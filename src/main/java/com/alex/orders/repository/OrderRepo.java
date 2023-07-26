package com.alex.orders.repository;

import com.alex.orders.entity.Order;
import com.alex.orders.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
  boolean existsOrderWithProducts(List<Product> products);
}
