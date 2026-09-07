package com.gabrielmacavilca.troubleshooting.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "APP_ORDER")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_order_seq")
    @SequenceGenerator(
            name = "app_order_seq",
            sequenceName = "SEQ_APP_ORDER",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;

    protected OrderEntity() {
    }

    public OrderEntity(Long productId, Integer quantity, String status) {
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}