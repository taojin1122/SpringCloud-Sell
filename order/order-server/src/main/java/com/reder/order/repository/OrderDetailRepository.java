package com.reder.order.repository;

import com.reder.order.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    /**
     * 查询订单详情
     * @param orderId
     * @return
     */
            List<OrderDetail> findByOrderId(String orderId);
}
