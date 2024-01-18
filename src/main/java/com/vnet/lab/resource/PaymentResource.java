package com.vnet.lab.resource;

import com.vnet.lab.dto.PaymentDto;
import com.vnet.lab.service.PaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Path("/payments")
public class PaymentResource {

    @Inject
    PaymentService paymentService;

    /**
     * Listing all payments: HTTP GET on path /api/payments
     * @return List of PaymentDto
     */
    @GET
    public List<PaymentDto> findAll(){
        return this.paymentService.findAll();
    }

    /**
     * Retrieving payment for a given payment id: HTTP GET on path /api/payments/{id}
     * @param id paymentId
     * @return PaymentDto
     */
    @GET
    @Path("/{id}")
    public PaymentDto findById(@PathParam("id") Long id){
        return this.paymentService.findById(id);
    }

    /**
     * Creating a new payment: HTTP POST on path /api/payments
     * @param paymentDto new payment to be created
     * @return paymentDto new payment created
     */
    @POST
    public PaymentDto create(PaymentDto paymentDto){
        return this.paymentService.create(paymentDto);
    }

    /**
     * Deleting payment for a given payment id: HTTP DELETE on path /api/payments/{id}
     * @param id id of the payment to be deleted
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id){
        this.paymentService.delete(id);
    }

    /**
     * Listing payments for a given price range: HTTP GET on path /api/payments/price/{max}
     * @param max largest price on range
     * @return List of PaymentDto
     */
    @GET
    @Path("/price/{max}")
    public List<PaymentDto> findPaymentByAmountRangeMax(@PathParam("max") double max){
        return this.paymentService.findByPriceRange(max);
    }

}
