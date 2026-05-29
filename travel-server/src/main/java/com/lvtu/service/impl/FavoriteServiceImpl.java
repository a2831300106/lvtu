package com.lvtu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvtu.common.PageResult;
import com.lvtu.common.Result;
import com.lvtu.common.SecurityUtils;
import com.lvtu.dto.FavoriteDto;
import com.lvtu.entity.Favorite;
import com.lvtu.mapper.FavoriteMapper;
import com.lvtu.service.FavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;

    public FavoriteServiceImpl(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    @Override
    public Result<PageResult<Favorite>> list(Integer page, Integer pageSize, String targetType) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<Favorite>()
                .eq(Favorite::getUserId, userId);
        if (targetType != null && !targetType.isEmpty()) {
            wrapper.eq(Favorite::getTargetType, targetType);
        }
        wrapper.orderByDesc(Favorite::getCreateTime);
        Page<Favorite> p = new Page<>(page, pageSize);
        favoriteMapper.selectPage(p, wrapper);
        PageResult<Favorite> result = new PageResult<>(p.getRecords(), p.getTotal(), page, pageSize);
        return Result.success(result);
    }

    @Override
    public Result<Void> add(FavoriteDto dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        Favorite exist = favoriteMapper.selectOne(
                new LambdaQueryWrapper<Favorite>()
                        .eq(Favorite::getUserId, userId)
                        .eq(Favorite::getTargetType, dto.getTargetType())
                        .eq(Favorite::getTargetId, dto.getTargetId())
        );
        if (exist == null) {
            Favorite favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setTargetType(dto.getTargetType());
            favorite.setTargetId(dto.getTargetId());
            favoriteMapper.insert(favorite);
        }
        return Result.success();
    }

    @Override
    public Result<Void> remove(FavoriteDto dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        favoriteMapper.delete(
                new LambdaQueryWrapper<Favorite>()
                        .eq(Favorite::getUserId, userId)
                        .eq(Favorite::getTargetType, dto.getTargetType())
                        .eq(Favorite::getTargetId, dto.getTargetId())
        );
        return Result.success();
    }
}
