package com.alex.orders.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.alex.orders.entity.Order;
import com.alex.orders.entity.OrderProducts;
import com.alex.orders.repository.OrderRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

  @InjectMocks
  private OrderService orderService;

  @Mock
  private OrderRepo orderRepository;

  @Test
  public void testGetOrderById() {
    // Mock the behavior of the orderRepository.findById method
    Long orderId = 1L;
    Order mockOrder = new Order(orderId, "Sample Order", null, 100.0);
    Mockito
      .when(orderRepository.findById(orderId))
      .thenReturn(Optional.of(mockOrder));

    // Call the getOrderById method in OrderService and verify the result
    Order result = orderService.getOrderById(orderId);
    assertNotNull(result);
    assertEquals(orderId, result.getOrderId());
  }

  @Test
  public void testCreateOrder() {
    // Create dummy OrderProducts data
    List<OrderProducts> dummyOrderProducts = new ArrayList<>();
    dummyOrderProducts.add(new OrderProducts(1L, null, 1L, 5, 10.0));
    dummyOrderProducts.add(new OrderProducts(2L, null, 2L, 3, 15.0));

    // Mock the behavior of the orderRepository.save method
    Order mockOrder = new Order(1L, "Sample Order", dummyOrderProducts, 100.0);
    Mockito
      .when(orderRepository.save(Mockito.any(Order.class)))
      .thenReturn(mockOrder);

    // Call the createOrder method in OrderService and verify the result
    Order newOrder = new Order(null, "New Order", dummyOrderProducts, 150.0);
    Order result = orderService.createOrder(newOrder);
    assertNotNull(result);
    assertNotNull(result.getOrderId());
    assertEquals("New Order", result.getOrderDescription());
    assertEquals(150.0, result.getOrderPrice(), 0.01);
    assertEquals(dummyOrderProducts, result.getOrderProducts());
  }
}
