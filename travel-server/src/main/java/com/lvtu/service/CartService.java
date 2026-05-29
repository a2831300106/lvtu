package com.lvtu.service;

import com.lvtu.common.Result;
import com.lvtu.dto.CartDto;
import com.lvtu.vo.CartVo;
import java.util.List;

public interface CartService {
    Result<Void> addItem(CartDto dto);
    Result<Void> updateItem(CartDto dto);
    Result<Void> removeItem(Long productId, String skuType);
    Result<List<CartVo>> list();
    Result<Void> clear();
}
