package com.vnet.lab.service;

import com.vnet.lab.dto.ReviewDto;
import com.vnet.lab.entity.Product;
import com.vnet.lab.entity.Review;
import com.vnet.lab.repository.ProductRepository;
import com.vnet.lab.repository.ReviewRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@Transactional
public class ReviewService {

    @Inject
    ReviewRepository reviewRepository;

    @Inject
    ProductRepository productRepository;

    public List<ReviewDto> findReviewsByProductId(Long id){
        log.debug("Request to get all Review for Product with id: {}", id);
        return this.reviewRepository.findReviewsByProductId(id).stream()
                .map(ReviewService::mapToDto).collect(Collectors.toList());
    }

    public ReviewDto findById(Long id){
        log.debug("Request to get Review with id: {}", id);
        return this.reviewRepository.findById(id)
                .map(ReviewService::mapToDto).orElse(null);
    }

    public ReviewDto create(ReviewDto reviewDto, Long productId){
        log.debug("Request to create Review: {} for the Product with Id: {}", reviewDto, productId);

        Product product = this.productRepository.findById(productId)
                .orElseThrow(()->new IllegalStateException("Product with id: {}" + productId + " was not found!"));
        Review savedReview = this.reviewRepository.saveAndFlush(new Review(reviewDto.getTitle(),
                reviewDto.getDescription(), reviewDto.getRating()));
        product.getReviews().add(savedReview);
        this.productRepository.saveAndFlush(product);

        return mapToDto(savedReview);
    }

    public void delete(Long reviewId){
        log.debug("Request to delete Review with Id: {}", reviewId);

        Review review = this.reviewRepository.findById(reviewId)
                .orElseThrow(()->new IllegalStateException("Review with Id: {}" + reviewId + " was not found!"));
        Product product = this.productRepository.findProductByReviewId(reviewId);
        product.getReviews().remove(review);
        this.productRepository.saveAndFlush(product);
        this.reviewRepository.delete(review);
    }

    public static ReviewDto mapToDto(Review review) {
        return new ReviewDto(review.getId(), review.getTitle(), review.getDescription(), review.getRating());
    }
}
