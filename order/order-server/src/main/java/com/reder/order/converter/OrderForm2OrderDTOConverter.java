package com.reder.order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reder.order.dataobject.OrderDetail;
import com.reder.order.dto.OrderDTO;
import com.reder.order.enums.ResultEnum;
import com.reder.order.exception.OrderException;
import com.reder.order.from.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){
                                    }.getType());
        } catch (Exception e) {
            log.error("【json转换错误】，string = {}",orderForm.getItems());
           throw  new OrderException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
