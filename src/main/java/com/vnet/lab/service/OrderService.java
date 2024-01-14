package com.vnet.lab.service;

import com.vnet.lab.dto.OrderDto;
import com.vnet.lab.dto.OrderItemDto;
import com.vnet.lab.entity.Order;
import com.vnet.lab.repository.CartRepository;
import com.vnet.lab.repository.OrderRepository;
import com.vnet.lab.repository.PaymentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@Transactional
public class OrderService {
    
    @Inject
    OrderRepository orderRepository;
    
    @Inject
    PaymentRepository paymentRepository;
    
    @Inject
    CartRepository cartRepository;
    
    public List<OrderDto> findAll(){
        log.debug("Request to get all Orders!");
        return this.orderRepository.findAll().stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());
    }

    private static OrderDto mapToDto(Order order) {
        Set<OrderItemDto> orderItems = order.getOrderItems().stream()
                .map(OrderItemService::mapToDto).collect(Collectors.toSet());
        return new OrderDto(order.getId(), order.getPrice(), order.getStatus().name(), order.getShipped(),
                order.getPayment() != null ? order.getPayment().getId() : null,
                AddressService.mapToDto(order.getShipmentAddress()),
                orderItems, CartService.mapToDto(order.getCart()));
    }

}
