package com.reder.order.config;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 *
 */
@RestController
@DefaultProperties(defaultFallback = "defaultFallback" )
public class HystrixController {
/**     // 超时设置
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//    })*/
    @HystrixCommand(commandProperties = {
             @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 设置熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), //  断路器的最小请求数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 断路器尝试连接的窗口休眠时间
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")  //  断路器打开的错误率
    })
    @GetMapping("/getProductInfoList")
    public String getProductInfoList() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://127.0.0.1:8005/product/listForProduct",
                                        Arrays.asList("157875196366160022"),
                                        String.class);
    }
    private String fallback() {
        return "太拥挤了，请稍后再试...";
    }
    private String defaultFallback() {
        return "默认提示：太拥挤了，请稍后再试...";
    }
}
