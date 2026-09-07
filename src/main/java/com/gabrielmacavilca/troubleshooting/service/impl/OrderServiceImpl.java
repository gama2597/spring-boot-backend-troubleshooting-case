package com.gabrielmacavilca.troubleshooting.service.impl;

import com.gabrielmacavilca.troubleshooting.dto.CreateOrderRequest;
import com.gabrielmacavilca.troubleshooting.entity.OrderEntity;
import com.gabrielmacavilca.troubleshooting.repository.OrderRepository;
import com.gabrielmacavilca.troubleshooting.service.InventoryService;
import com.gabrielmacavilca.troubleshooting.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            InventoryService inventoryService
    ) {
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    @Transactional
    public OrderEntity createOrder(CreateOrderRequest request) {
        OrderEntity order = new OrderEntity(
                request.productId(),
                request.quantity(),
                "CREATED"
        );

        order = orderRepository.saveAndFlush(order);

        log.info(
                "Order persisted. orderId={}, productId={}, quantity={}",
                order.getId(),
                order.getProductId(),
                order.getQuantity()
        );

        inventoryService.reserveStock(
                request.productId(),
                request.quantity()
        );

        return order;
    }
}
