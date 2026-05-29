package com.lvtu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvtu.common.PageResult;
import com.lvtu.common.Result;
import com.lvtu.common.SecurityUtils;
import com.lvtu.dto.CartDto;
import com.lvtu.dto.OrderCreateDto;
import com.lvtu.dto.OrderPayDto;
import com.lvtu.entity.Order;
import com.lvtu.entity.OrderItem;
import com.lvtu.mapper.OrderItemMapper;
import com.lvtu.mapper.OrderMapper;
import com.lvtu.service.OrderService;
import com.lvtu.vo.OrderVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    public OrderServiceImpl(OrderMapper orderMapper, OrderItemMapper orderItemMapper,
                            RedisTemplate<String, Object> redisTemplate) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Result<OrderVo> create(OrderCreateDto dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        Order order = new Order();
        String orderNo = UUID.randomUUID().toString().replace("-", "").substring(0, 20).toUpperCase();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setProductId(dto.getProductId());
        order.setProductName(dto.getProductName());
        order.setDate(LocalDate.parse(dto.getDate()));
        order.setTicketType(dto.getTicketType());
        order.setQuantity(dto.getQuantity());
        order.setUnitPrice(dto.getUnitPrice());
        BigDecimal totalAmount = dto.getUnitPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        order.setTotalAmount(totalAmount);
        order.setPayAmount(totalAmount);
        order.setContactName(dto.getContactName());
        order.setContactPhone(dto.getContactPhone());
        order.setRemark(dto.getRemark());
        order.setStatus("pending");
        orderMapper.insert(order);

        OrderItem item = new OrderItem();
        item.setOrderId(order.getId());
        item.setProductId(dto.getProductId());
        item.setProductName(dto.getProductName());
        item.setTicketType(dto.getTicketType());
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());
        item.setSubtotal(totalAmount);
        orderItemMapper.insert(item);

        String cartKey = "cart:" + userId;
        String field = dto.getProductId() + ":" + dto.getTicketType();
        redisTemplate.opsForHash().delete(cartKey, field);

        OrderVo vo = buildOrderVo(order);
        return Result.success(vo);
    }

    @Override
    public Result<OrderVo> getDetail(String orderNo) {
        Order order = orderMapper.selectOne(
                new LambdaQueryWrapper<Order>().eq(Order::getOrderNo, orderNo)
        );
        if (order == null) {
            return Result.error("订单不存在");
        }
        OrderVo vo = buildOrderVo(order);
        return Result.success(vo);
    }

    @Override
    public Result<PageResult<OrderVo>> list(String status, Integer page, Integer pageSize) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, userId);
        if (status != null && !status.isEmpty()) {
            wrapper.eq(Order::getStatus, status);
        }
        wrapper.orderByDesc(Order::getCreateTime);
        Page<Order> p = new Page<>(page, pageSize);
        orderMapper.selectPage(p, wrapper);
        PageResult<OrderVo> result = new PageResult<>(
                p.getRecords().stream().map(this::buildOrderVo).toList(),
                p.getTotal(), page, pageSize
        );
        return Result.success(result);
    }

    @Override
    public Result<Void> pay(OrderPayDto dto) {
        Order order = orderMapper.selectById(dto.getOrderId());
        if (order == null) {
            return Result.error("订单不存在");
        }
        if (!"pending".equals(order.getStatus())) {
            return Result.error("订单状态不正确");
        }
        order.setStatus("paid");
        order.setPayMethod(dto.getPayMethod());
        String voucherCode = UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        order.setVoucherCode(voucherCode);
        orderMapper.updateById(order);
        return Result.success();
    }

    @Override
    public Result<Void> cancel(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            return Result.error("订单不存在");
        }
        if (!"pending".equals(order.getStatus())) {
            return Result.error("只能取消待支付订单");
        }
        order.setStatus("cancelled");
        orderMapper.updateById(order);
        return Result.success();
    }

    @Override
    public Result<Void> refund(Long orderId, String reason) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            return Result.error("订单不存在");
        }
        if (!"paid".equals(order.getStatus())) {
            return Result.error("只能退款已支付订单");
        }
        order.setStatus("refunded");
        orderMapper.updateById(order);
        return Result.success();
    }

    private OrderVo buildOrderVo(Order order) {
        OrderVo vo = new OrderVo();
        vo.setId(order.getId());
        vo.setOrderNo(order.getOrderNo());
        vo.setProductId(order.getProductId());
        vo.setProductName(order.getProductName());
        vo.setDate(order.getDate());
        vo.setTicketName(order.getTicketName());
        vo.setQuantity(order.getQuantity());
        vo.setPayAmount(order.getPayAmount());
        vo.setStatus(order.getStatus());
        vo.setContactName(order.getContactName());
        vo.setContactPhone(order.getContactPhone());
        vo.setCreateTime(order.getCreateTime());
        vo.setVoucherCode(order.getVoucherCode());
        vo.setRemark(order.getRemark());
        return vo;
    }
}
