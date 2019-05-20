package com.reder.product.service.impl;

import com.reder.product.dataproject.ProductCategory;
import com.reder.product.repository.ProductCategoryRepository;
import com.reder.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findBycategoryTypeIn( categoryTypeList);
    }
}
