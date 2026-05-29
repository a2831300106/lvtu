package com.lvtu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvtu.common.Result;
import com.lvtu.common.SecurityUtils;
import com.lvtu.dto.CartDto;
import com.lvtu.entity.ProductSku;
import com.lvtu.mapper.ProductMapper;
import com.lvtu.mapper.ProductSkuMapper;
import com.lvtu.service.CartService;
import com.lvtu.vo.CartVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductMapper productMapper;
    private final ProductSkuMapper productSkuMapper;
    private final ObjectMapper objectMapper;

    public CartServiceImpl(RedisTemplate<String, Object> redisTemplate,
                           ProductMapper productMapper,
                           ProductSkuMapper productSkuMapper,
                           ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.productMapper = productMapper;
        this.productSkuMapper = productSkuMapper;
        this.objectMapper = objectMapper;
    }

    private String getCartKey() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("未登录");
        }
        return "cart:" + userId;
    }

    @Override
    public Result<Void> addItem(CartDto dto) {
        String key = getCartKey();
        String field = dto.getProductId() + ":" + dto.getSkuType();
        Object oldValue = redisTemplate.opsForHash().get(key, field);
        if (oldValue != null) {
            try {
                CartDto oldCart = objectMapper.readValue(oldValue.toString(), CartDto.class);
                dto.setQuantity(oldCart.getQuantity() + dto.getQuantity());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            redisTemplate.opsForHash().put(key, field, objectMapper.writeValueAsString(dto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }

    @Override
    public Result<Void> updateItem(CartDto dto) {
        String key = getCartKey();
        String field = dto.getProductId() + ":" + dto.getSkuType();
        try {
            redisTemplate.opsForHash().put(key, field, objectMapper.writeValueAsString(dto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }

    @Override
    public Result<Void> removeItem(Long productId, String skuType) {
        String key = getCartKey();
        String field = productId + ":" + skuType;
        redisTemplate.opsForHash().delete(key, field);
        return Result.success();
    }

    @Override
    public Result<List<CartVo>> list() {
        String key = getCartKey();
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        List<CartVo> cartVos = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            try {
                CartDto cartDto = objectMapper.readValue(entry.getValue().toString(), CartDto.class);
                CartVo vo = new CartVo();
                vo.setProductId(cartDto.getProductId());
                vo.setSkuType(cartDto.getSkuType());
                vo.setQuantity(cartDto.getQuantity());
                String productName = productMapper.selectById(cartDto.getProductId()).getName();
                vo.setProductName(productName);
                ProductSku sku = productSkuMapper.selectOne(
                        new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<ProductSku>()
                                .eq(ProductSku::getProductId, cartDto.getProductId())
                                .eq(ProductSku::getType, cartDto.getSkuType())
                );
                if (sku != null) {
                    vo.setSkuName(sku.getName());
                    vo.setPrice(sku.getPrice());
                }
                if (vo.getPrice() != null && vo.getQuantity() != null) {
                    vo.setSubtotal(vo.getPrice().multiply(BigDecimal.valueOf(vo.getQuantity())));
                }
                cartVos.add(vo);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return Result.success(cartVos);
    }

    @Override
    public Result<Void> clear() {
        String key = getCartKey();
        redisTemplate.delete(key);
        return Result.success();
    }
}
