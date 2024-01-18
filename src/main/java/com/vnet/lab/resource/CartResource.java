package com.vnet.lab.resource;

import com.vnet.lab.dto.CartDto;
import com.vnet.lab.service.CartService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/carts")
@Tag(name = "cart", description = "All cart methods")
public class CartResource {

    @Inject
    CartService cartService;

    /**
     * Listing all carts: HTTP GET on path /api/carts
     * @return List of CartDto
     */
    @GET
    public List<CartDto> findAll(){
        return this.cartService.findAll();
    }

    /**
     * Listing active carts: HTTP GET on path /api/carts/active
     * @return List of CartDto
     */
    @GET
    @Path("/active")
    public List<CartDto> findAllActiveCarts(){
        return this.cartService.findAllActiveCarts();
    }

    /**
     * Listing active cart for a given customer id: HTTP GET on path /api/carts/customer/{id}
     * @param id customerId
     * @return CartDto
     */
    @GET
    @Path("/customer/{id}")
    public CartDto getActiveCartForCustomer(@PathParam("id") Long id){
        return this.cartService.getActiveCart(id);
    }

    /**
     * Retrieving cart for a given cart id: HTTP GET on path /api/carts/{id}
     * @param id cartId
     * @return CartDto
     */
    @GET
    @Path("/{id}")
    public CartDto findById(@PathParam("id") Long id){
        return this.cartService.findById(id);
    }

    /**
     * Creating a new cart for a given customer id: HTTP POST on path /api/carts/customer/{id}
     * @param customerId customerId
     * @return CartDto
     */
    @POST
    @Path("/customer/{id}")
    public CartDto create(@PathParam("id") Long customerId){
        return this.cartService.createDto(customerId);
    }

    /**
     * Deleting cart for a given cart id: HTTP DELETE on path /api/carts/{id}
     * @param id cartId
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id){
        this.cartService.delete(id);
    }

}
