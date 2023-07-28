package com.alex.orders.controller;

import com.alex.orders.entity.Order;
import com.alex.orders.exceptions.OrderProductAlreadyExistsException;
import com.alex.orders.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordersapi")
public class OrderController {

  @Autowired
  OrderService orderService;

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    Order order = orderService.getOrderById(id);
    if (order != null) {
      return ResponseEntity.ok(order);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders() {
    List<Order> orders = orderService.getAllOrders();
    return ResponseEntity.ok(orders);
  }

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    try {
      Order savedOrder = orderService.createOrder(order);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    } catch (OrderProductAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
    if (orderService.deleteOrderById(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
} //End of OrderController
