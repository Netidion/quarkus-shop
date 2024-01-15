package com.vnet.lab.service;

import com.vnet.lab.dto.ProductDto;
import com.vnet.lab.entity.Product;
import com.vnet.lab.repository.CategoryRepository;
import com.vnet.lab.repository.ProductRepository;
import com.vnet.lab.utils.enums.ProductStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@Transactional
public class ProductService {
    
    @Inject
    ProductRepository productRepository;
    
    @Inject
    CategoryRepository categoryRepository;
    
    public List<ProductDto> findAll(){
        log.debug("Request to get all Products!");
        return this.productRepository.findAll().stream()
                .map(ProductService::mapToDto).collect(Collectors.toList());
    }

    public ProductDto findById(Long id){
        log.debug("Request to get Product with id: {}", id);
        return this.productRepository.findById(id)
                .map(ProductService::mapToDto).orElse(null);
    }

    public Long countAll(){
        return this.productRepository.count();
    }

    public Long countByCategoryId(Long id){
        return this.productRepository.countAllByCategoryId(id);
    }

    public ProductDto create(ProductDto productDto){
        log.debug("Request to create Product: {}", productDto);
        return mapToDto(this.productRepository.save(new Product(
                productDto.getName(), productDto.getDescription(), productDto.getPrice(),
                ProductStatus.valueOf(productDto.getStatus()), productDto.getSalesCounter(),
                Collections.emptySet(), categoryRepository.findById(productDto.getCategoryId()).orElse(null))
        ));
    }

    public void delete(Long id){
        log.debug("Request to delete Product with id: {}", id);
        this.productRepository.deleteById(id);
    }

    public List<ProductDto> findByCategoryId(Long id){
        log.debug("Request to get Product with Category id: {}", id);
        return this.productRepository.findAllByCategoryId(id).stream()
                .map(ProductService::mapToDto).collect(Collectors.toList());
    }

    private static ProductDto mapToDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getProductStatus().name(), product.getSalesCounter(),
                product.getReviews().stream().map(ReviewService::mapToDto).collect(Collectors.toSet()),
                product.getCategory().getId());
    }
}
