package com.vnet.lab.resource;

import com.vnet.lab.dto.OrderDto;
import com.vnet.lab.service.OrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Path("/orders")
public class OrderResource {

    @Inject
    OrderService orderService;

    /**
     * Retrieving all orders: HTTP GET on path /api/orders
     * @return List of OrderDto
     */
    @GET
    public List<OrderDto> findAll(){
        return this.orderService.findAll();
    }

    /**
     * Retrieving all orders for a given customer id: HTTP GET on path /api/orders/customer/{id}
     * @param customerId customerId
     * @return List od OrderDto
     */
    @GET
    @Path("/customer/{id}")
    public List<OrderDto> findAllByCustomer(@PathParam("id") Long customerId){
        return this.orderService.findAllByCustomerId(customerId);
    }

    /**
     * Retrieving order with given id: HTTP GET on path /api/orders/{id}
     * @param id orderId
     * @return OrderDto
     */
    @GET
    @Path("/{id}")
    public OrderDto findById(@PathParam("id") Long id){
        return this.orderService.findById(id);
    }

    /**
     * Creating a new order: HTTP POST on path /api/orders
     * @param orderDto order to be created
     * @return OrderDto order created
     */
    @POST
    public OrderDto create(OrderDto orderDto){
        return this.orderService.create(orderDto);
    }

    /**
     * Deleting an order with given id: HTTP DELETE on path /api/orders/{id}
     * @param id orderId
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id){
        this.orderService.delete(id);
    }

    /**
     * Checking if an order with given id exists: HTTP GET on path /api/orders/exists/{id}
     * @param id orderId
     * @return true/false if order exists or not
     */
    @GET
    @Path("/exists/{id}")
    public boolean existsById(@PathParam("id") Long id){
        return this.orderService.existsById(id);
    }

}
