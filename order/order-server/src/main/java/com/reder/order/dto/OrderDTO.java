package com.reder.order.dto;

import com.reder.order.dataobject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {

    /**
     * 买家id
     */
    private String orderId;
    /**
     * 买家地址
     */
    private String buyerName;
    /**
     * 买家手机号
     */
    private String buyerPhone;
    /**
     * 买家微信OpenId
     */
    private String buyerOpenid;
    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;
    /**
     *订单状态，默认为0新下单
     */
    private Integer orderStatus;
    /**
     * 支付状态，默认为0未支付。
     */
    private Integer payStatus;

    private String buyerAddress;

    private List<OrderDetail> orderDetailList;
}
