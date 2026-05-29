package com.lvtu.service.impl;

import com.lvtu.common.Result;
import com.lvtu.entity.Category;
import com.lvtu.mapper.CategoryMapper;
import com.lvtu.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Result<List<Category>> list() {
        List<Category> categories = categoryMapper.selectList(null);
        return Result.success(categories);
    }
}
