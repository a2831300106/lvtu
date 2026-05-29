package com.lvtu.service;

import com.lvtu.common.PageResult;
import com.lvtu.common.Result;
import com.lvtu.entity.Product;
import com.lvtu.vo.ProductDetailVo;

public interface ProductService {
    Result<ProductDetailVo> getDetail(Long id);
    Result<PageResult<Product>> list(String keyword, String category, Integer page, Integer pageSize);
}
