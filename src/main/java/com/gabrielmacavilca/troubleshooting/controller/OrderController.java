package com.gabrielmacavilca.troubleshooting.controller;

import com.gabrielmacavilca.troubleshooting.dto.CreateOrderRequest;
import com.gabrielmacavilca.troubleshooting.entity.OrderEntity;
import com.gabrielmacavilca.troubleshooting.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(
            @Valid @RequestBody CreateOrderRequest request
    ) {
        OrderEntity order = orderService.createOrder(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(order);
    }
}