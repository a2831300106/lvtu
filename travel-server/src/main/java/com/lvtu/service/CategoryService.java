package com.lvtu.service;

import com.lvtu.common.Result;
import com.lvtu.entity.Category;
import java.util.List;

public interface CategoryService {
    Result<List<Category>> list();
}
