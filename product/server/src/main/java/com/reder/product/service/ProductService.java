package com.reder.product.service;

import com.reder.product.common.DecreaseStockInput;
import com.reder.product.dataproject.ProductInfo;
import com.reder.product.dto.CartDTO;

import java.util.List;

public interface ProductService {
    /**
     * 查询所有商品在架列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 根據商品ID，查詢
     * @return
     */
    List<ProductInfo> findByProductId(List<String> list);

    /**
     * 扣库存
     * @param decreaseStockInputList
     */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
