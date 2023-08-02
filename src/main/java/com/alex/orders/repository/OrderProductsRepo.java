package com.alex.orders.repository;

import com.alex.orders.entity.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductsRepo extends JpaRepository<OrderProducts, Long> {
  boolean existsByProductIdAndProductQuantity(
    Long productId,
    int productQuantity
  );
}
