package com.vnet.lab.resource;

import com.vnet.lab.dto.ProductDto;
import com.vnet.lab.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Path("/products")
public class ProductResource {

    @Inject
    ProductService productService;

    /**
     * Listing all products: HTTP GET on path /api/products
     * @return List of ProductDto
     */
    @GET
    public List<ProductDto> findAll(){
        return this.productService.findAll();
    }

    /**
     * Counting all products: HTTP GET on path /api/products/count
     * @return Long the total number of Products
     */
    @GET
    @Path("/count")
    public Long countAllProducts(){
        return this.productService.countAll();
    }

    /**
     * Retrieving product for a given product id: HTTP GET on path /api/products/{id}
     * @param id productId
     * @return ProductDto
     */
    @GET
    @Path("/{id}")
    public ProductDto findById(@PathParam("id") Long id) {
        return this.productService.findById(id);
    }

    /**
     * Retrieving products for a given category id: HTTP GET on path /api/products/category/{id}
     * @param id categoryId
     * @return List of ProductDto
     */
    @GET
    @Path("/category/{id}")
    public List<ProductDto> findByCategoryId(@PathParam("id") Long id){
        return this.productService.findByCategoryId(id);
    }

    /**
     * Total number of Products under the category with given id: HTTP GET on path /api/products/count/category/{id}
     * @param id categoryId
     * @return Long number of products
     */
    @GET
    @Path("/count/category/{id}")
    public Long countByCategoryId(@PathParam("id") Long id) {
        return this.productService.countByCategoryId(id);
    }

    /**
     * Creating a new product: HTTP POST on path /api/products
     * @param productDto new product to be created
     * @return ProductDto new product created
     */
    @POST
    public ProductDto create(ProductDto productDto){
        return this.productService.create(productDto);
    }

    /**
     * Deleting product for a given product id: HTTP DELETE on path /api/products/{id}
     * @param id id of product to be deleted
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id){
        this.productService.delete(id);
    }






















}
