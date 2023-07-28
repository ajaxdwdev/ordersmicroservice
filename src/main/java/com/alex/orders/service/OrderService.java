package com.alex.orders.service;

import com.alex.orders.entity.Order;
import com.alex.orders.entity.OrderProducts;
import com.alex.orders.exceptions.OrderProductAlreadyExistsException;
import com.alex.orders.repository.OrderProductsRepo;
import com.alex.orders.repository.OrderRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Autowired
  OrderRepo orderRepo;

  @Autowired
  OrderProductsRepo orderProductsRepo;

  public Order getOrderById(Long id) {
    return orderRepo.findById(id).orElse(null);
  }

  public List<Order> getAllOrders() {
    return orderRepo.findAll();
  }

  public Order createOrder(Order order) {
    if (orderRepo.existsById(order.getOrderId())) {
      // Check if any of the orderProducts already exist in the database.
      for (OrderProducts orderProduct : order.getOrderProducts()) {
        if (
          orderProduct.getId() != null &&
          orderProductsRepo.existsById(orderProduct.getId())
        ) {
          throw new OrderProductAlreadyExistsException(
            "OrderProduct with ID " + orderProduct.getId() + " already exists."
          );
        }
      }
    }

    return orderRepo.save(order);
  }

  public boolean deleteOrderById(Long id) {
    if (orderRepo.existsById(id)) {
      orderRepo.deleteById(id);
      return true;
    }
    return false;
  }
}
