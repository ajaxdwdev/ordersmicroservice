package com.alex.orders.controller;

import com.alex.orders.entity.Order;
import com.alex.orders.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

  @InjectMocks
  private OrderController orderController;

  @Mock
  private OrderService orderService;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
  }

  @Test
  public void testGetOrderById() throws Exception {
    // Mock the behavior of the getOrderById method in the OrderService
    Long orderId = 1L;
    Order mockOrder = new Order(orderId, "Sample Order", null, 100.0);
    Mockito.when(orderService.getOrderById(orderId)).thenReturn(mockOrder);

    // Use MockMvc to perform the HTTP GET request and verify the result
    mockMvc
      .perform(MockMvcRequestBuilders.get("/orders/{id}", orderId))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(orderId));
  }

  @Test
  public void testCreateOrder() throws Exception {
    // Mock the behavior of the createOrder method in the OrderService
    Order mockOrder = new Order(1L, "Sample Order", null, 100.0);
    Mockito
      .when(orderService.createOrder(Mockito.any(Order.class)))
      .thenReturn(mockOrder);

    // Use MockMvc to perform the HTTP POST request and verify the result
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/orders")
          .contentType(MediaType.APPLICATION_JSON)
          .content(
            "{ \"orderDescription\": \"New Order\", \"orderPrice\": 150.0 }"
          )
      )
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").exists())
      .andExpect(
        MockMvcResultMatchers
          .jsonPath("$.orderDescription")
          .value("Sample Order")
      )
      .andExpect(MockMvcResultMatchers.jsonPath("$.orderPrice").value(100.0));
  }
}
