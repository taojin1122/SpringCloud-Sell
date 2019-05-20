package com.reder.product.controller;

import com.reder.product.VO.ProductInfoVO;
import com.reder.product.VO.ProductVO;
import com.reder.product.VO.ResultVO;
import com.reder.product.common.DecreaseStockInput;
import com.reder.product.dataproject.ProductCategory;
import com.reder.product.dataproject.ProductInfo;
import com.reder.product.dto.CartDTO;
import com.reder.product.service.CategoryService;
import com.reder.product.service.ProductService;
import com.reder.product.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
   @Autowired
   private ProductService productService;
   @Autowired
   private CategoryService categoryService;

    /**
     *
     * @return
     */
    @RequestMapping("list")
    public ResultVO<ProductVO> list () {
        // 1、查询所有在架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        // 2、 获取类目type列表
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType).collect(Collectors.toList());
        // 3、从数据库查询类目
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
       // 4、构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
               if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                   ProductInfoVO productInfoVO = new ProductInfoVO();
                   BeanUtils.copyProperties(productInfo,productInfoVO);
                   productInfoVOList.add(productInfoVO);
               }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }

    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList){
        return productService.findByProductId(productIdList);
    }
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> cartDTOList ) {
        productService.decreaseStock(cartDTOList);
    }
}
