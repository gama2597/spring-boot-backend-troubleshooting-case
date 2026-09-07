package com.gabrielmacavilca.troubleshooting.service.impl;

import com.gabrielmacavilca.troubleshooting.entity.Inventory;
import com.gabrielmacavilca.troubleshooting.exception.InsufficientStockException;
import com.gabrielmacavilca.troubleshooting.repository.InventoryRepository;
import com.gabrielmacavilca.troubleshooting.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void reserveStock(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found: " + productId)
                );

        if (inventory.getAvailableStock() < quantity) {
            log.warn(
                    "Insufficient stock. productId={}, requested={}, available={}",
                    productId,
                    quantity,
                    inventory.getAvailableStock()
            );

            throw new InsufficientStockException(
                    productId,
                    quantity,
                    inventory.getAvailableStock()
            );
        }

        inventory.setAvailableStock(
                inventory.getAvailableStock() - quantity
        );

        inventoryRepository.saveAndFlush(inventory);

        log.info(
                "Stock reserved. productId={}, quantity={}, remaining={}",
                productId,
                quantity,
                inventory.getAvailableStock()
        );
    }
}
