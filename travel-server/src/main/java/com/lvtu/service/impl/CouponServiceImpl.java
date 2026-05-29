package com.lvtu.service.impl;

import com.lvtu.common.Result;
import com.lvtu.common.SecurityUtils;
import com.lvtu.entity.Coupon;
import com.lvtu.entity.UserCoupon;
import com.lvtu.mapper.CouponMapper;
import com.lvtu.mapper.UserCouponMapper;
import com.lvtu.service.CouponService;
import com.lvtu.vo.UserCouponVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    private final CouponMapper couponMapper;
    private final UserCouponMapper userCouponMapper;

    public CouponServiceImpl(CouponMapper couponMapper, UserCouponMapper userCouponMapper) {
        this.couponMapper = couponMapper;
        this.userCouponMapper = userCouponMapper;
    }

    @Override
    public Result<List<UserCouponVo>> getUserCoupons() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        List<UserCoupon> userCoupons = userCouponMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<UserCoupon>()
                        .eq(UserCoupon::getUserId, userId)
                        .orderByDesc(UserCoupon::getCreateTime)
        );
        List<UserCouponVo> vos = new ArrayList<>();
        for (UserCoupon uc : userCoupons) {
            Coupon coupon = couponMapper.selectById(uc.getCouponId());
            if (coupon == null) continue;
            UserCouponVo vo = new UserCouponVo();
            vo.setId(uc.getId());
            vo.setCouponId(coupon.getId());
            vo.setName(coupon.getName());
            vo.setAmount(coupon.getAmount());
            vo.setCondition(coupon.getConditionAmount() != null ?
                    "满" + coupon.getConditionAmount() + "元可用" : "无门槛");
            vo.setExpireTime(uc.getExpireTime());
            vo.setUsed(uc.getUsed());
            vos.add(vo);
        }
        return Result.success(vos);
    }

    @Override
    public Result<Void> receive(Long couponId) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        Coupon coupon = couponMapper.selectById(couponId);
        if (coupon == null) {
            return Result.error("优惠券不存在");
        }
        if (coupon.getTotal() != null && coupon.getReceived() != null
                && coupon.getReceived() >= coupon.getTotal()) {
            return Result.error("优惠券已领完");
        }
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(couponId);
        userCoupon.setUsed(false);
        if (coupon.getExpireDays() != null) {
            userCoupon.setExpireTime(LocalDateTime.now().plusDays(coupon.getExpireDays()));
        }
        userCouponMapper.insert(userCoupon);
        if (coupon.getReceived() == null) {
            coupon.setReceived(1);
        } else {
            coupon.setReceived(coupon.getReceived() + 1);
        }
        couponMapper.updateById(coupon);
        return Result.success();
    }
}
