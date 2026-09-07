package com.gabrielmacavilca.troubleshooting.service;

import com.gabrielmacavilca.troubleshooting.dto.CreateOrderRequest;
import com.gabrielmacavilca.troubleshooting.entity.OrderEntity;

public interface OrderService {
    public OrderEntity createOrder(CreateOrderRequest request);
}
