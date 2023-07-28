package com.alex.orders.exceptions;

public class OrderProductAlreadyExistsException extends RuntimeException {

  public OrderProductAlreadyExistsException() {
    super();
  }

  public OrderProductAlreadyExistsException(String message) {
    super(message);
  }

  public OrderProductAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public OrderProductAlreadyExistsException(Throwable cause) {
    super(cause);
  }
}
