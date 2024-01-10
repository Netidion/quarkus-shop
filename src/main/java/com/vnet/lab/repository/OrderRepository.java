package com.vnet.lab.repository;

import com.vnet.lab.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCartCustomerId(Long customerId);
    List<Order> findByPaymentId(Long id);
}
