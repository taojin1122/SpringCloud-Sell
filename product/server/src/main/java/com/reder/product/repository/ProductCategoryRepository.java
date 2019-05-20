package com.reder.product.repository;

import com.reder.product.dataproject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    List<ProductCategory> findBycategoryTypeIn(List<Integer> categoryTypeList);
}
