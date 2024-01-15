package com.vnet.lab.service;

import com.vnet.lab.dto.CategoryDto;
import com.vnet.lab.dto.ProductDto;
import com.vnet.lab.entity.Category;
import com.vnet.lab.repository.CategoryRepository;
import com.vnet.lab.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
@Transactional
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    ProductRepository productRepository;

    public static CategoryDto mapToDto(Category category, Long productsCount){
        return new CategoryDto(category.getId(), category.getName(), category.getDescription(), productsCount);
    }

    public List<CategoryDto> findAll(){
        log.debug("Request to get all Categories!");
        return this.categoryRepository.findAll().stream()
                .map(category -> mapToDto(category, productRepository.countAllByCategoryId(category.getId())))
                .collect(Collectors.toList());
    }

    public CategoryDto findById(Long id){
        log.debug("Request to get Category: {}", id);
        return this.categoryRepository.findById(id)
                .map(category -> mapToDto(category, productRepository.countAllByCategoryId(category.getId())))
                .orElse(null);
    }

    public CategoryDto create(CategoryDto categoryDto){
        log.debug("Request to create Category: {}", categoryDto);
        return mapToDto(this.categoryRepository
                .save(new Category(categoryDto.getName(), categoryDto.getDescription())),
                this.productRepository.countAllByCategoryId(categoryDto.getId()));
    }

    public void delete(Long id){
        log.debug("Request to delete Category: {}", id);
        log.debug("Deleting all products for the Category: {}", id);

        this.productRepository.deleteAllByCategoryId(id);
    }

    public List<ProductDto> findProductsByCategoryId(Long id){
        return this.productRepository.findAllByCategoryId(id).stream()
                .map(ProductService::mapToDto)
                .collect(Collectors.toList());
    }
}
