package com.lvtu.controller;

import com.lvtu.common.Result;
import com.lvtu.service.CouponService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/list")
    public Result<?> list() {
        return couponService.getUserCoupons();
    }

    @PostMapping("/receive/{couponId}")
    public Result<?> receive(@PathVariable Long couponId) {
        return couponService.receive(couponId);
    }
}
