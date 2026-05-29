package com.lvtu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvtu.common.PageResult;
import com.lvtu.common.Result;
import com.lvtu.entity.Category;
import com.lvtu.entity.Product;
import com.lvtu.entity.ProductDate;
import com.lvtu.entity.ProductSku;
import com.lvtu.mapper.CategoryMapper;
import com.lvtu.mapper.ProductDateMapper;
import com.lvtu.mapper.ProductMapper;
import com.lvtu.mapper.ProductSkuMapper;
import com.lvtu.service.ProductService;
import com.lvtu.vo.ProductDetailVo;
import com.lvtu.vo.SkuVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductSkuMapper productSkuMapper;
    private final ProductDateMapper productDateMapper;
    private final CategoryMapper categoryMapper;

    public ProductServiceImpl(ProductMapper productMapper, ProductSkuMapper productSkuMapper,
                              ProductDateMapper productDateMapper, CategoryMapper categoryMapper) {
        this.productMapper = productMapper;
        this.productSkuMapper = productSkuMapper;
        this.productDateMapper = productDateMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Result<ProductDetailVo> getDetail(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            return Result.error("产品不存在");
        }
        ProductDetailVo vo = new ProductDetailVo();
        vo.setId(product.getId());
        vo.setName(product.getName());
        vo.setCategoryId(product.getCategoryId());
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            vo.setImages(Arrays.asList(product.getImages().split(",")));
        }
        vo.setPrice(product.getPrice());
        vo.setOriginalPrice(product.getOriginalPrice());
        vo.setDescription(product.getDescription());
        if (product.getIncludes() != null && !product.getIncludes().isEmpty()) {
            vo.setIncludes(Arrays.asList(product.getIncludes().split(",")));
        }
        if (product.getRules() != null && !product.getRules().isEmpty()) {
            vo.setRules(Arrays.asList(product.getRules().split(",")));
        }
        vo.setOpenTime(product.getOpenTime());
        List<ProductSku> skuList = productSkuMapper.selectList(
                new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, id)
        );
        List<SkuVo> skuVos = skuList.stream().map(sku -> {
            SkuVo skuVo = new SkuVo();
            skuVo.setId(sku.getId());
            skuVo.setName(sku.getName());
            skuVo.setType(sku.getType());
            skuVo.setPrice(sku.getPrice());
            skuVo.setStock(sku.getStock());
            return skuVo;
        }).collect(Collectors.toList());
        vo.setSkus(skuVos);
        return Result.success(vo);
    }

    @Override
    public Result<PageResult<Product>> list(String keyword, String category, Integer page, Integer pageSize) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<Product>()
                .eq(Product::getStatus, 1);
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Product::getName, keyword);
        }
        if (category != null && !category.isEmpty()) {
            Category cat = categoryMapper.selectOne(
                    new LambdaQueryWrapper<Category>().eq(Category::getName, category)
            );
            if (cat != null) {
                wrapper.eq(Product::getCategoryId, cat.getId());
            }
        }
        wrapper.orderByDesc(Product::getSales);
        Page<Product> p = new Page<>(page, pageSize);
        productMapper.selectPage(p, wrapper);
        PageResult<Product> result = new PageResult<>(p.getRecords(), p.getTotal(), page, pageSize);
        return Result.success(result);
    }
}
