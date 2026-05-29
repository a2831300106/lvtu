package com.lvtu.service;

import com.lvtu.common.PageResult;
import com.lvtu.common.Result;
import com.lvtu.dto.FavoriteDto;
import com.lvtu.entity.Favorite;

public interface FavoriteService {
    Result<PageResult<Favorite>> list(Integer page, Integer pageSize, String targetType);
    Result<Void> add(FavoriteDto dto);
    Result<Void> remove(FavoriteDto dto);
}
