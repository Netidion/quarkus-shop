package com.vnet.lab.resource;

import com.vnet.lab.dto.CategoryDto;
import com.vnet.lab.dto.ProductDto;
import com.vnet.lab.service.CategoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/categories")
@Tag(name = "category", description = "All category methods")
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    /**
     * Listing all categories: HTTP GET on path /api/categories
     * @return CategoryDto
     */
    @GET
    public List<CategoryDto> findAll(){
        return this.categoryService.findAll();
    }

    /**
     *
     * Retrieving category for a given category id: HTTP GET on path /api/categories/{id}
     * @param id categoryId
     * @return CategoryDto
     */
    @GET
    @Path("/{id}")
    public CategoryDto findById(@PathParam("id") Long id) {
        return this.categoryService.findById(id);
    }

    /**
     * Listing products for a given category id: HTTP GET on path /api/categories/{id}/products
     * @param id categoryId
     * @return ProductDto
     */
    @GET
    @Path("/{id}/products")
    public List<ProductDto> findProductsByCategoryId(@PathParam("id") Long id){
        return this.categoryService.findProductsByCategoryId(id);
    }

    /**
     * Creating a new category: HTTP POST on path /api/categories
     * @param categoryDto new category to be inserted
     * @return CategoryDto new category inserted
     */
    @POST
    public CategoryDto create(CategoryDto categoryDto){
        return this.categoryService.create(categoryDto);
    }

    /**
     * Deleting category for a given category id: HTTP DELETE on path /api/categories/{id}
     * @param id category id
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id){
        this.categoryService.delete(id);
    }
}
