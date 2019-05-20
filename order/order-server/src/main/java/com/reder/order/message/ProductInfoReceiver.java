package com.reder.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.reder.order.utils.JsonUtil;
import com.reder.order.utils.JsonUtils;
import com.reder.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductInfoReceiver {
    private static final String PRODUCT_STOCK = "product_stock_%s";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message) {
        // message  => ProductInfoOutput
       List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>)JsonUtils.fromJson(message, new TypeReference<List<ProductInfoOutput>>(){});
       log.info("从队列【{}】接收消息：{}","productInfo",productInfoOutputList);


       //存储到redis中
         for (ProductInfoOutput productInfoOutput : productInfoOutputList) {
             stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK,productInfoOutput.getProductId()),
                     String.valueOf(productInfoOutput.getProductStock()));
         }
    }

}
