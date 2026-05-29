package com.lvtu.service;

import com.lvtu.common.PageResult;
import com.lvtu.common.Result;
import com.lvtu.dto.AdminLoginDto;
import com.lvtu.dto.PageDto;
import com.lvtu.dto.ProductDto;
import com.lvtu.dto.UserDto;
import com.lvtu.entity.Order;
import com.lvtu.entity.Product;
import com.lvtu.entity.User;
import com.lvtu.vo.LoginVo;
import com.lvtu.vo.StatsVo;

public interface AdminService {
    Result<LoginVo> login(AdminLoginDto dto);
    Result<StatsVo> getStats();
    Result<PageResult<User>> userList(PageDto dto);
    Result<Void> createUser(UserDto dto);
    Result<Void> updateUser(Long id, UserDto dto);
    Result<Void> deleteUser(Long id);
    Result<PageResult<Product>> productList(PageDto dto);
    Result<Void> createProduct(ProductDto dto);
    Result<Void> updateProduct(Long id, ProductDto dto);
    Result<Void> toggleProductStatus(Long id);
    Result<Void> deleteProduct(Long id);
    Result<PageResult<Order>> orderList(PageDto dto);
    Result<Order> getOrderDetail(Long id);
    Result<Void> updateOrderStatus(Long id, String status);
}
