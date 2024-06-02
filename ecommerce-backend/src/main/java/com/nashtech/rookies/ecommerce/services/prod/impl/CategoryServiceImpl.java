package com.nashtech.rookies.ecommerce.services.prod.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.models.prod.Category;
import com.nashtech.rookies.ecommerce.models.prod.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.prod.requests.CategoryRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryPaginationDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.CategoryResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.repositories.prod.CategoryRepository;
import com.nashtech.rookies.ecommerce.repositories.prod.ProductRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.CategoryService;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl extends CommonServiceImpl<Category, Long> implements CategoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    CategoryServiceImpl(CategoryRepository categoryRepository,
            ProductRepository productRepository) {
        super(categoryRepository);
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        if (!categoryRepository.existsByCategoryName(categoryRequestDTO.categoryName())) {
            Category category = new Category();
            category.setCategoryName(categoryRequestDTO.categoryName());
            category.setCategoryDesc(categoryRequestDTO.categoryDesc());
            category.setActiveMode(categoryRequestDTO.activeMode());
            category.setProducts(new HashSet<>());
            category = categoryRepository.saveAndFlush(category);
            return new CategoryResponseDTO(category.getId(),
                    category.getCategoryName(), category.getCategoryDesc(),
                    category.getActiveMode(), new HashSet<>());
        } else {
            Category category = categoryRepository.findByCategoryName(categoryRequestDTO.categoryName());
            throw new ResourceConflictException("Existed this Category with an id: " + category.getId());
        }
    }

    @Override
    public ResponseEntity<?> handleGetCategory(CategoryGetRequestParamsDTO requestParamsDTO) {
        CategoryPaginationDTO categoryResponseDTOs;
        CategoryResponseDTO categoryResponseDTO;

        if (requestParamsDTO.id() != null) {
            categoryResponseDTO = getCategoryById(requestParamsDTO.id());
            return ResponseEntity.ok(categoryResponseDTO);
        } else {
            categoryResponseDTOs = getCategories(requestParamsDTO.dir(),
                    requestParamsDTO.pageNum() - 1, requestParamsDTO.pageSize());
            return ResponseEntity.ok(categoryResponseDTOs);
        }
    }

    public CategoryPaginationDTO getCategories(Sort.Direction dir, int pageNum, int pageSize) {
        Sort sort = Sort.by(dir, "id");
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        List<Product> products = productRepository.findAll();
        Page<Category> categories = categoryRepository.findAll(pageable);

        List<CategoryResponseDTO> categoryResponseDTOs = new ArrayList<>();
        categories.forEach(category -> {
            Set<Product> productResponse = new HashSet<>();
            products.forEach(product -> {
                if (product.getCategory().getId() != null) {
                    if (product.getCategory().getId().equals(category.getId())) {
                        productResponse.add(product);
                    }
                } else {
                    throw new NotFoundException("Category with id: " + category.getId() + " does not have this " +
                            "Product with an id: " + product.getId());
                }
            });
            category.setProducts(productResponse);
            Set<Long> productIds = new HashSet<>();
            productResponse.forEach(product -> productIds.add(product.getId()));
            categoryResponseDTOs.add(new CategoryResponseDTO(
                    category.getId(), category.getCategoryName(),
                    category.getCategoryDesc(), category.getActiveMode(), productIds));
        });
        return new CategoryPaginationDTO(categories.getTotalPages(), categories.getTotalElements(),
                categories.getSize(), categories.getNumber(), categoryResponseDTOs);
    }

    public CategoryResponseDTO getCategoryById(Long id) {
        if (categoryRepository.existsById(id)) {
            var category = categoryRepository.findById(id).get();
            Set<Long> productIds = category.getProducts().stream().map(Persistable::getId).collect(Collectors.toSet());
            return new CategoryResponseDTO(id,
                    category.getCategoryName(), category.getCategoryDesc(),
                    category.getActiveMode(), productIds);
        } else {
            throw new NotFoundException("Not found Category with an id: " + id);
        }
    }

    @Override
    @Transactional
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        if (categoryRepository.existsById(id)) {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Not found Category with an id: " + id));
            category.setCategoryName(categoryRequestDTO.categoryName());
            category.setCategoryDesc(categoryRequestDTO.categoryDesc());
            category.setActiveMode(categoryRequestDTO.activeMode());
            category = categoryRepository.saveAndFlush(category);
            Set<Long> productIds = new HashSet<>();
            category.getProducts().forEach(prod -> productIds.add(prod.getId()));
            return new CategoryResponseDTO(category.getId(),
                    category.getCategoryName(), category.getCategoryDesc(),
                    category.getActiveMode(), productIds);
        } else {
            throw new NotFoundException("Not found Category with an id: " + id);
        }
    }
}
