package com.alex.orders.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.orders.entity.Order;
import com.alex.orders.repository.OrderRepo;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    public List<Order> getAllOrder() {
        List<Order> ordersList = orderRepo.findAll();
        
        if(ordersList.size() > 0 ){
            return ordersList;
        }else{
            return new ArrayList<Order>();
        }
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepo.findById(id);
    }

    public Order createOrder(Order order) {
        Order newOrder = new Order();
        newOrder.setProductDescription(order.getProductDescription());
        newOrder.setOrderPrice(order.getOrderPrice());
        newOrder.setProducts(order.getProducts());
        newOrder = orderRepo.save(newOrder);

        return newOrder;
    }

    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }


}
