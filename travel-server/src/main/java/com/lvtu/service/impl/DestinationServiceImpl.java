package com.lvtu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lvtu.common.Result;
import com.lvtu.entity.Category;
import com.lvtu.entity.Product;
import com.lvtu.mapper.CategoryMapper;
import com.lvtu.mapper.ProductMapper;
import com.lvtu.service.DestinationService;
import com.lvtu.vo.DestinationVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DestinationServiceImpl implements DestinationService {

    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    public DestinationServiceImpl(ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Result<List<DestinationVo>> list(String keyword) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<Product>()
                .eq(Product::getStatus, 1);
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Product::getName, keyword);
        }
        wrapper.orderByDesc(Product::getSales);
        List<Product> products = productMapper.selectList(wrapper);
        List<DestinationVo> vos = products.stream().map(p -> {
            DestinationVo vo = new DestinationVo();
            vo.setId(p.getId());
            vo.setName(p.getName());
            vo.setImage(p.getImage());
            vo.setDescription(p.getDescription());
            Category category = categoryMapper.selectById(p.getCategoryId());
            if (category != null && "景点门票".equals(category.getName())) {
                vo.setRating(5.0);
            }
            return vo;
        }).collect(Collectors.toList());
        return Result.success(vos);
    }
}
