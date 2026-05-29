package com.lvtu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvtu.common.JwtUtils;
import com.lvtu.common.PageResult;
import com.lvtu.common.Result;
import com.lvtu.dto.AdminLoginDto;
import com.lvtu.dto.PageDto;
import com.lvtu.dto.ProductDto;
import com.lvtu.dto.SkuItemDto;
import com.lvtu.dto.UserDto;
import com.lvtu.entity.AdminUser;
import com.lvtu.entity.Category;
import com.lvtu.entity.Order;
import com.lvtu.entity.Product;
import com.lvtu.entity.ProductSku;
import com.lvtu.entity.User;
import com.lvtu.mapper.AdminUserMapper;
import com.lvtu.mapper.CategoryMapper;
import com.lvtu.mapper.OrderMapper;
import com.lvtu.mapper.ProductMapper;
import com.lvtu.mapper.ProductSkuMapper;
import com.lvtu.mapper.UserMapper;
import com.lvtu.service.AdminService;
import com.lvtu.vo.CategoryStatVo;
import com.lvtu.vo.LoginVo;
import com.lvtu.vo.StatsVo;
import com.lvtu.vo.UserInfoVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminUserMapper adminUserMapper;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final ProductSkuMapper productSkuMapper;
    private final OrderMapper orderMapper;
    private final CategoryMapper categoryMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminUserMapper adminUserMapper, UserMapper userMapper,
                            ProductMapper productMapper, ProductSkuMapper productSkuMapper,
                            OrderMapper orderMapper, CategoryMapper categoryMapper,
                            JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.adminUserMapper = adminUserMapper;
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.productSkuMapper = productSkuMapper;
        this.orderMapper = orderMapper;
        this.categoryMapper = categoryMapper;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Result<LoginVo> login(AdminLoginDto dto) {
        AdminUser admin = adminUserMapper.selectOne(
                new LambdaQueryWrapper<AdminUser>().eq(AdminUser::getUsername, dto.getUsername())
        );
        if (admin == null) {
            return Result.error("管理员不存在");
        }
        if (!passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            return Result.error("密码错误");
        }
        if (admin.getStatus() != null && admin.getStatus() == 0) {
            return Result.error("账号已禁用");
        }
        String token = jwtUtils.generateToken(admin.getId(), "ADMIN");
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        UserInfoVo userInfo = new UserInfoVo();
        userInfo.setId(admin.getId());
        userInfo.setNickname(admin.getNickname());
        loginVo.setUserInfo(userInfo);
        return Result.success(loginVo);
    }

    @Override
    public Result<StatsVo> getStats() {
        StatsVo stats = new StatsVo();
        Long totalUsers = userMapper.selectCount(null);
        stats.setTotalUsers(totalUsers);
        Long totalProducts = productMapper.selectCount(null);
        stats.setTotalProducts(totalProducts);
        Long totalOrders = orderMapper.selectCount(null);
        stats.setTotalOrders(totalOrders);
        Long todayOrders = orderMapper.selectCount(
                new LambdaQueryWrapper<Order>()
                        .ge(Order::getCreateTime, LocalDate.now().atStartOfDay())
        );
        stats.setTodayOrders(todayOrders);
        List<Long> trend = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            Long count = orderMapper.selectCount(
                    new LambdaQueryWrapper<Order>()
                            .ge(Order::getCreateTime, date.atStartOfDay())
                            .lt(Order::getCreateTime, date.plusDays(1).atStartOfDay())
            );
            trend.add(count);
            labels.add(date.format(DateTimeFormatter.ofPattern("MM-dd")));
        }
        stats.setOrderTrend(trend);
        stats.setTrendLabels(labels);
        List<Category> categories = categoryMapper.selectList(null);
        List<CategoryStatVo> categoryStats = categories.stream().map(cat -> {
            CategoryStatVo csv = new CategoryStatVo();
            csv.setName(cat.getName());
            Long count = productMapper.selectCount(
                    new LambdaQueryWrapper<Product>().eq(Product::getCategoryId, cat.getId())
            );
            csv.setValue(count);
            return csv;
        }).collect(Collectors.toList());
        stats.setCategoryStats(categoryStats);
        return Result.success(stats);
    }

    @Override
    public Result<PageResult<User>> userList(PageDto dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
            wrapper.like(User::getPhone, dto.getKeyword())
                    .or(w -> w.like(User::getNickname, dto.getKeyword()));
        }
        wrapper.orderByDesc(User::getCreateTime);
        Page<User> p = new Page<>(dto.getPage(), dto.getPageSize());
        userMapper.selectPage(p, wrapper);
        PageResult<User> result = new PageResult<>(p.getRecords(), p.getTotal(), dto.getPage(), dto.getPageSize());
        return Result.success(result);
    }

    @Override
    public Result<Void> createUser(UserDto dto) {
        User user = new User();
        user.setPhone(dto.getPhone());
        user.setNickname(dto.getNickname());
        user.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        user.setLevel("bronze");
        user.setMemberSince(LocalDateTime.now());
        userMapper.insert(user);
        return Result.success();
    }

    @Override
    public Result<Void> updateUser(Long id, UserDto dto) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        if (dto.getNickname() != null) user.setNickname(dto.getNickname());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        if (dto.getStatus() != null) user.setStatus(dto.getStatus());
        userMapper.updateById(user);
        return Result.success();
    }

    @Override
    public Result<Void> deleteUser(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        user.setDeleted(1);
        userMapper.updateById(user);
        return Result.success();
    }

    @Override
    public Result<PageResult<Product>> productList(PageDto dto) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        if (dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
            wrapper.like(Product::getName, dto.getKeyword());
        }
        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            wrapper.eq(Product::getCategoryId, dto.getCategory());
        }
        wrapper.orderByDesc(Product::getCreateTime);
        Page<Product> p = new Page<>(dto.getPage(), dto.getPageSize());
        productMapper.selectPage(p, wrapper);
        PageResult<Product> result = new PageResult<>(p.getRecords(), p.getTotal(), dto.getPage(), dto.getPageSize());
        return Result.success(result);
    }

    @Override
    public Result<Void> createProduct(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setCategoryId(dto.getCategoryId());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        product.setSales(0);
        productMapper.insert(product);
        if (dto.getSkus() != null) {
            for (SkuItemDto skuDto : dto.getSkus()) {
                ProductSku sku = new ProductSku();
                sku.setProductId(product.getId());
                sku.setName(skuDto.getName());
                sku.setType(skuDto.getType());
                sku.setPrice(skuDto.getPrice());
                sku.setStock(skuDto.getStock());
                productSkuMapper.insert(sku);
            }
        }
        return Result.success();
    }

    @Override
    public Result<Void> updateProduct(Long id, ProductDto dto) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            return Result.error("产品不存在");
        }
        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getCategoryId() != null) product.setCategoryId(dto.getCategoryId());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getOriginalPrice() != null) product.setOriginalPrice(dto.getOriginalPrice());
        if (dto.getStatus() != null) product.setStatus(dto.getStatus());
        productMapper.updateById(product);
        if (dto.getSkus() != null) {
            productSkuMapper.delete(
                    new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, id)
            );
            for (SkuItemDto skuDto : dto.getSkus()) {
                ProductSku sku = new ProductSku();
                sku.setProductId(id);
                sku.setName(skuDto.getName());
                sku.setType(skuDto.getType());
                sku.setPrice(skuDto.getPrice());
                sku.setStock(skuDto.getStock());
                productSkuMapper.insert(sku);
            }
        }
        return Result.success();
    }

    @Override
    public Result<Void> toggleProductStatus(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            return Result.error("产品不存在");
        }
        product.setStatus(product.getStatus() == 1 ? 0 : 1);
        productMapper.updateById(product);
        return Result.success();
    }

    @Override
    public Result<Void> deleteProduct(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            return Result.error("产品不存在");
        }
        product.setDeleted(1);
        productMapper.updateById(product);
        return Result.success();
    }

    @Override
    public Result<PageResult<Order>> orderList(PageDto dto) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
            wrapper.like(Order::getOrderNo, dto.getKeyword())
                    .or(w -> w.like(Order::getProductName, dto.getKeyword()));
        }
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            wrapper.eq(Order::getStatus, dto.getStatus());
        }
        wrapper.orderByDesc(Order::getCreateTime);
        Page<Order> p = new Page<>(dto.getPage(), dto.getPageSize());
        orderMapper.selectPage(p, wrapper);
        PageResult<Order> result = new PageResult<>(p.getRecords(), p.getTotal(), dto.getPage(), dto.getPageSize());
        return Result.success(result);
    }

    @Override
    public Result<Order> getOrderDetail(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            return Result.error("订单不存在");
        }
        return Result.success(order);
    }

    @Override
    public Result<Void> updateOrderStatus(Long id, String status) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            return Result.error("订单不存在");
        }
        order.setStatus(status);
        orderMapper.updateById(order);
        return Result.success();
    }
}
