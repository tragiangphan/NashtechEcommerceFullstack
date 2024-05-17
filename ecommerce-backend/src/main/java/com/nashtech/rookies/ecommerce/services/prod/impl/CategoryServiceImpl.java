package com.nashtech.rookies.ecommerce.services.prod.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.models.prod.Category;
import com.nashtech.rookies.ecommerce.repositories.prod.CategoryRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.prod.CategoryService;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl extends CommonServiceImpl<Category, Long> implements CategoryService {

  private final CategoryRepository categoryRepository;

  CategoryServiceImpl(CategoryRepository categoryRepository) {
    super(categoryRepository);
    this.categoryRepository = categoryRepository;
  }

}
