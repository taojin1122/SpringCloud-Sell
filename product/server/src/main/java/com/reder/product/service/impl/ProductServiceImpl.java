package com.reder.product.service.impl;

import com.rabbitmq.tools.json.JSONUtil;
import com.reder.product.common.DecreaseStockInput;
import com.reder.product.common.ProductInfoOutput;
import com.reder.product.dataproject.ProductInfo;
import com.reder.product.dto.CartDTO;
import com.reder.product.enums.ProductStatusEnum;
import com.reder.product.enums.ResultEnum;
import com.reder.product.exception.ProductException;
import com.reder.product.repository.ProductInfoRepository;
import com.reder.product.service.ProductService;
import com.reder.product.utils.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findByProductId(List<String> list) {
        return productInfoRepository.findByProductIdIn(list);
    }

    /**
     * 扣库存
     * @param decreaseStockInputList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
         List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputList);
          List<ProductInfoOutput> list = productInfoList.stream().map(e -> {
              ProductInfoOutput output = new ProductInfoOutput();
              BeanUtils.copyProperties(e,output);
              return output;
          }).collect(Collectors.toList());
             //发送MQ消息
             ProductInfoOutput productInfoOutput = new ProductInfoOutput();
             //BeanUtils.copyProperties(productInfo,productInfoOutput);
             amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(list));
    }

    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> cartDTOList) {
        List<ProductInfo> list = new ArrayList<>();
        for (DecreaseStockInput cartDTO : cartDTOList) {
            Optional<ProductInfo> optional =  productInfoRepository.findById(cartDTO.getProductId());
            //判断商品是否存在
            if (!optional.isPresent()) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXISST);
            }
            ProductInfo productInfo = optional.get();
            //库存是否足够
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0){
                throw  new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            list.add(productInfo);
        }
        return list;
    }
}
