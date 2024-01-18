package com.vnet.lab.resource;

import com.vnet.lab.dto.OrderItemDto;
import com.vnet.lab.service.OrderItemService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/order-items")
@Tag(name = "order-item", description = "All order-item methods")
public class OrderItemResource {

    @Inject
    OrderItemService orderItemService;

    /**
     * Retrieving order items for a given order id: HTTP GET on path /api/order-items/order/{id}
     * @param id orderId
     * @return List of OrderItemDto
     */
    @GET
    @Path("/order/{id}")
    public List<OrderItemDto> findByOrderId(@PathParam("id") Long id){
        return this.orderItemService.findByOrderId(id);
    }

    /**
     * Retrieving order item for a given id: HTTP GET on path /api/order-items/{id}
     * @param id orderItemId
     * @return OrderItemDto
     */
    @GET
    @Path("{id}")
    public OrderItemDto findById(@PathParam("id") Long id){
        return this.orderItemService.findById(id);
    }

    /**
     * Creating a new order item: HTTP POST on path /api/order-items
     * @param orderItemDto OrderItem to be created
     * @return orderItemDto OrderItem created
     */
    @POST
    public OrderItemDto create(OrderItemDto orderItemDto){
        return this.orderItemService.create(orderItemDto);
    }

    /**
     * Deleting a new order item by a given id: HTTP DELETE on path /api/order-items
     * @param id OrderItemId to be deleted
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id){
        this.orderItemService.delete(id);
    }
}
