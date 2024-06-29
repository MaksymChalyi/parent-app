package com.maksimkaxxl.gatewayservicev2.service;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String message) {
    super(message);
  }
}
