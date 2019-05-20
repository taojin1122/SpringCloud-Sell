//package com.reder.order.controller;
//
//
//import com.reder.order.dataobject.ProductInfo;
//import com.reder.product.client.ProductClient;
//import com.reder.product.common.ProductInfoOutput;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//@Slf4j
//public class ClientController {
//
////  @Autowired
////  private ProductClient productClient;
//
////    @RequestMapping("getproductMsg")
////    public String getProductMsg() {
////
////        String  response = productClient.productMsg();
////        log.info("response={}",response);
////        return response;
////    }
//    @GetMapping("/getProductList")
//    public String getProductList() {
//        List<ProductInfoOutput> list = productClient.listForOrder(Arrays.asList("157875227953464068"));
//        log.info("list={}",list);
//        return "ok";
//    }
//}
