package com.lvtu.controller;

import com.lvtu.common.Result;
import com.lvtu.dto.OrderCreateDto;
import com.lvtu.dto.OrderPayDto;
import com.lvtu.dto.RefundDto;
import com.lvtu.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public Result<?> create(@RequestBody OrderCreateDto dto) {
        return orderService.create(dto);
    }

    @GetMapping("/detail/{orderNo}")
    public Result<?> detail(@PathVariable String orderNo) {
        return orderService.getDetail(orderNo);
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(required = false) String status,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return orderService.list(status, page, pageSize);
    }

    @PostMapping("/pay")
    public Result<?> pay(@RequestBody OrderPayDto dto) {
        return orderService.pay(dto);
    }

    @PostMapping("/cancel/{orderId}")
    public Result<?> cancel(@PathVariable Long orderId) {
        return orderService.cancel(orderId);
    }

    @PostMapping("/refund/{orderId}")
    public Result<?> refund(@PathVariable Long orderId,
                            @RequestBody(required = false) RefundDto dto) {
        String reason = dto != null ? dto.getReason() : null;
        return orderService.refund(orderId, reason);
    }
}
