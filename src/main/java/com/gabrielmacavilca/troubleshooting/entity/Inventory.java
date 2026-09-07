package com.gabrielmacavilca.troubleshooting.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "INVENTORY",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_INVENTORY_PRODUCT",
                columnNames = "PRODUCT_ID"
        )
)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_seq")
    @SequenceGenerator(
            name = "inventory_seq",
            sequenceName = "SEQ_INVENTORY",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "AVAILABLE_STOCK", nullable = false)
    private Integer availableStock;

    protected Inventory() {
    }

    public Inventory(Long productId, Integer availableStock) {
        this.productId = productId;
        this.availableStock = availableStock;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }
}