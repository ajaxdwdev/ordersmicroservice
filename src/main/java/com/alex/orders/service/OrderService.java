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

  public Order createOrder(Order order)
    throws OrderProductAlreadyExistsException {
    if (
      order.getOrderId() != null && orderRepo.existsById(order.getOrderId())
    ) {
      throw new OrderProductAlreadyExistsException(
        "Order with ID " + order.getOrderId() + " already exists."
      );
    }
    // Check if any of the orderProducts already exist in the database.
    for (OrderProducts orderProduct : order.getOrderProducts()) {
      if (
        orderProductsRepo.existsByProductIdAndProductQuantity(
          orderProduct.getProductId(),
          orderProduct.getProductQuantity()
        )
      ) {
        throw new OrderProductAlreadyExistsException(
          "OrderProduct with productId " +
          orderProduct.getProductId() +
          " and productQuantity " +
          orderProduct.getProductQuantity() +
          " already exists. You already have an order for this item."
        );
      }
    }
    //create the new order
    Order newOrder = new Order();
    newOrder.setOrderId(order.getOrderId());
    newOrder.setOrderDescription(order.getOrderDescription());
    newOrder.setOrderPrice(order.getOrderPrice());
    for (OrderProducts product : order.getOrderProducts()) {
      OrderProducts newOrderProduct = new OrderProducts();
      newOrderProduct.setOrder(newOrder);
      newOrderProduct.setProductId(product.getProductId());
      newOrderProduct.setProductQuantity(product.getProductQuantity());
      newOrderProduct.setPricePerUnit(product.getPricePerUnit());
      newOrder.add(newOrderProduct);
    }
    return orderRepo.save(newOrder);
  }

  public boolean deleteOrderById(Long id) {
    if (orderRepo.existsById(id)) {
      orderRepo.deleteById(id);
      return true;
    }
    return false;
  }
}
