package com.gabrielmacavilca.troubleshooting.repository;

import com.gabrielmacavilca.troubleshooting.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}