package com.vnet.lab.resource;

import com.vnet.lab.dto.CustomerDto;
import com.vnet.lab.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Path("/customers")
public class CustomerResource {

    @Inject
    CustomerService customerService;

    /**
     * Listing all customers: HTTP GET on path /api/customers
     * @return List of CustomerDto
     */
    @GET
    public List<CustomerDto> findAll(){
        return this.customerService.findAll();
    }

    /**
     * Retrieving customer for a given customer id: HTTP GET on path /api/customers/{id}
     * @param id customerId
     * @return CustomerDto
     */
    @GET
    @Path("/{id}")
    public CustomerDto findById(@PathParam("id") Long id){
        return this.customerService.findById(id);
    }

    /**
     * Retrieving all active customers: HTTP GET on path /api/customers/active
     * @return List of CustomerDto
     */
    @GET
    @Path("/active")
    public List<CustomerDto> findAllActive(){
        return this.customerService.findAllActive();
    }

    /**
     * Retrieving all active customers: HTTP GET on path /api/customers/inactive
     * @return List of CustomerDto
     */
    @GET
    @Path("/inactive")
    public List<CustomerDto> findAllInactive(){
        return this.customerService.findAllInactive();
    }

    /**
     * Creating a new customer: HTTP POST on path /api/customers
     * @param customerDto new customer to be created
     * @return CustomerDto new customer created
     */
    @POST
    public CustomerDto create(CustomerDto customerDto){
        return this.customerService.create(customerDto);
    }

    /**
     * Deleting a customer for a given customer id: HTTP DELETE on path /api/customers/{id}
     * @param id customerId
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id){
        this.customerService.delete(id);
    }
}
