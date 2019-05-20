package com.reder.product.service;

import com.reder.product.dataproject.ProductCategory;

import java.util.List;

public interface CategoryService {
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
