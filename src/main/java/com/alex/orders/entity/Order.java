package com.alex.orders.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "order_desc")
  private String productDescription;

  @OneToMany(cascade = CascadeType.ALL) // Establishes a one-to-many relationship with Product
  @JoinColumn(name = "order_id")
  private List<Product> products;

  @Column(name = "order_price")
  private double orderPrice;

  public void setProducts(List<Product> products) {
    this.products = products;
    calculateOrderPrice();
  }

  private void calculateOrderPrice() {
    this.orderPrice =
      products
        .stream()
        .mapToDouble(product ->
          product.getPricePerUnit() * product.getProductQuantity()
        )
        .sum();
  }
}
