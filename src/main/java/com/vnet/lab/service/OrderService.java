package com.vnet.lab.service;

import com.vnet.lab.dto.OrderDto;
import com.vnet.lab.dto.OrderItemDto;
import com.vnet.lab.entity.Cart;
import com.vnet.lab.entity.Order;
import com.vnet.lab.repository.CartRepository;
import com.vnet.lab.repository.OrderRepository;
import com.vnet.lab.repository.PaymentRepository;
import com.vnet.lab.utils.enums.OrderStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.EMPTY_SET;

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

    public OrderDto findById(Long id){
        log.debug("Request to get Order with id: {]" + id);
        return this.orderRepository.findById(id)
                .map(OrderService::mapToDto).orElse(null);
    }

    public List<OrderDto> findAllByCustomerId(Long id){
        log.debug("Request to get all orders for Customer with id: {}", id);
        return this.orderRepository.findByCartCustomerId(id).stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());
    }

    public OrderDto create(OrderDto orderDto){
        log.debug("Request to create Order: {}", orderDto);
        Long cartId = orderDto.getCart().getId();
        Cart cart = this.cartRepository.findById(cartId).orElseThrow(()->
                new IllegalStateException("The Cart with id: " + cartId + " was not found!"));
        return mapToDto(this.orderRepository.save(new Order(BigDecimal.ZERO, OrderStatus.CREATION,
                null, null,
                AddressService.createFromDto(orderDto.getShipmentAddress()),
                EMPTY_SET, cart)));
    }

    @Transactional
    public void delete(Long id){
        log.debug("Request to delete Order with id: {}", id);
        Order order = this.orderRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("Order with id: {] " + id + " was not found!"));
        Optional.ofNullable(order.getPayment()).ifPresent(paymentRepository::delete);

        orderRepository.delete(order);
    }

    public boolean existsById(Long id){
        return this.orderRepository.existsById(id);
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
