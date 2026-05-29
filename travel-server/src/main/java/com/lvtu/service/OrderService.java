package com.lvtu.service;

import com.lvtu.common.PageResult;
import com.lvtu.common.Result;
import com.lvtu.dto.OrderCreateDto;
import com.lvtu.dto.OrderPayDto;
import com.lvtu.vo.OrderVo;

public interface OrderService {
    Result<OrderVo> create(OrderCreateDto dto);
    Result<OrderVo> getDetail(String orderNo);
    Result<PageResult<OrderVo>> list(String status, Integer page, Integer pageSize);
    Result<Void> pay(OrderPayDto dto);
    Result<Void> cancel(Long orderId);
    Result<Void> refund(Long orderId, String reason);
}
