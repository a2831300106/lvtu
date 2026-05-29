package com.lvtu.service;

import com.lvtu.common.Result;
import com.lvtu.vo.UserCouponVo;
import java.util.List;

public interface CouponService {
    Result<List<UserCouponVo>> getUserCoupons();
    Result<Void> receive(Long couponId);
}
