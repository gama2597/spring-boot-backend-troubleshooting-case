package com.gabrielmacavilca.troubleshooting.service;

import com.gabrielmacavilca.troubleshooting.dto.CreateOrderRequest;
import com.gabrielmacavilca.troubleshooting.entity.Inventory;
import com.gabrielmacavilca.troubleshooting.entity.OrderEntity;
import com.gabrielmacavilca.troubleshooting.exception.InsufficientStockException;
import com.gabrielmacavilca.troubleshooting.repository.InventoryRepository;
import com.gabrielmacavilca.troubleshooting.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceIntegrationTest {

    private static final Long PRODUCT_ID = 1001L;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void resetDatabase() {
        orderRepository.deleteAll();
        inventoryRepository.deleteAll();

        inventoryRepository.saveAndFlush(
                new Inventory(PRODUCT_ID, 5)
        );
    }

    @Test
    void shouldRollbackOrderWhenStockIsInsufficient() {

        CreateOrderRequest request =
                new CreateOrderRequest(PRODUCT_ID, 10);

        assertThrows(
                InsufficientStockException.class,
                () -> orderService.createOrder(request)
        );

        assertEquals(0, orderRepository.count());

        Inventory inventory = inventoryRepository
                .findByProductId(PRODUCT_ID)
                .orElseThrow();

        assertEquals(5, inventory.getAvailableStock());
    }

    @Test
    void shouldCommitOrderAndInventoryWhenStockIsAvailable() {

        CreateOrderRequest request =
                new CreateOrderRequest(PRODUCT_ID, 2);

        OrderEntity order = orderService.createOrder(request);

        assertNotNull(order.getId());
        assertEquals(1, orderRepository.count());

        OrderEntity persistedOrder =
                orderRepository.findById(order.getId()).orElseThrow();

        assertEquals(PRODUCT_ID, persistedOrder.getProductId());
        assertEquals(2, persistedOrder.getQuantity());
        assertEquals("CREATED", persistedOrder.getStatus());

        Inventory inventory = inventoryRepository
                .findByProductId(PRODUCT_ID)
                .orElseThrow();

        assertEquals(3, inventory.getAvailableStock());
    }
}