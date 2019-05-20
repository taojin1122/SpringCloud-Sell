package com.reder.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    @RequestMapping("/msg")
     public String message(){
         return "this is a message";
     }
}
