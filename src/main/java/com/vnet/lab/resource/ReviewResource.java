package com.vnet.lab.resource;

import com.vnet.lab.dto.ReviewDto;
import com.vnet.lab.service.ReviewService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Authenticated
@Path("/reviews")
@Tag(name = "review", description = "All review methods")
public class ReviewResource {

    @Inject
    ReviewService reviewService;

    /**
     * Listing all reviews regarding the product with given id: HTTP GET on path /api/reviews/product/{id}
     * @param id productId
     * @return List of ReviewDto
     */
    @GET
    @Path("/product/{id}")
    public List<ReviewDto> findAllByProductId(@PathParam("id") Long id){
        return this.reviewService.findReviewsByProductId(id);
    }

    /**
     * Retrieving review with given id: HTTP GET on path /api/reviews/{id}
     * @param id reviewId
     * @return ReviewDto
     */
    @GET
    @Path("/{id}")
    public ReviewDto findById(@PathParam("id") Long id) {
        return this.reviewService.findById(id);
    }

    /**
     * Creating a review for a product with given id: HTTP POST on path /api/reviews/product/{id}
     * @param reviewDto review to be created
     * @param id productId
     * @return ReviewDto review created
     */
    @POST
    @Path("/product/{id}")
    public ReviewDto create(ReviewDto reviewDto, @PathParam("id") Long id){
        return this.reviewService.create(reviewDto, id);
    }

    /**
     * Deleting a review with given id: HTTP POST on path /api/reviews/{id}
     * @param id reviewId
     */
    @RolesAllowed("admin")
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id){
        this.reviewService.delete(id);
    }

}
