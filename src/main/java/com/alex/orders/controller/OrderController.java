package com.alex.orders.controller;

import java.util.List;
import java.util.Optional;

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

import com.alex.orders.entity.Order;
import com.alex.orders.service.OrderService;

@RestController
@RequestMapping("/ordersapi")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrder(){
        List<Order> orders = orderService.getAllOrder();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        if(!order.isPresent()) {
			return ResponseEntity.notFound().build();
		}
        return new ResponseEntity<>(order.get(), HttpStatus.OK);
    }

    @PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		Order saved = orderService.createOrder(order);
		return new ResponseEntity<Order>(saved, HttpStatus.CREATED);
	}

    @DeleteMapping("/{id}")
	public ResponseEntity<Order>deleteOrder(@PathVariable("id")Long id) {
        Optional<Order> removedOrder = orderService.getOrderById(id);
		if(!removedOrder.isPresent()){
            return ResponseEntity.notFound().build();
        }
		orderService.deleteOrder(id);
		return ResponseEntity.ok().build();
	}
}//End of OrderController
