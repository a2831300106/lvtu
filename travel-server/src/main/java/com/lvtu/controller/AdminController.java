package com.lvtu.controller;

import com.lvtu.common.Result;
import com.lvtu.dto.*;
import com.lvtu.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody AdminLoginDto dto) {
        return adminService.login(dto);
    }

    @GetMapping("/stats")
    public Result<?> stats() {
        return adminService.getStats();
    }

    @GetMapping("/user/list")
    public Result<?> userList(PageDto dto) {
        return adminService.userList(dto);
    }

    @PostMapping("/user/create")
    public Result<?> createUser(@RequestBody UserDto dto) {
        return adminService.createUser(dto);
    }

    @PutMapping("/user/update/{id}")
    public Result<?> updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
        return adminService.updateUser(id, dto);
    }

    @DeleteMapping("/user/delete/{id}")
    public Result<?> deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }

    @GetMapping("/product/list")
    public Result<?> productList(PageDto dto) {
        return adminService.productList(dto);
    }

    @PostMapping("/product/create")
    public Result<?> createProduct(@RequestBody ProductDto dto) {
        return adminService.createProduct(dto);
    }

    @PutMapping("/product/update/{id}")
    public Result<?> updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) {
        return adminService.updateProduct(id, dto);
    }

    @PutMapping("/product/status/{id}")
    public Result<?> toggleProductStatus(@PathVariable Long id) {
        return adminService.toggleProductStatus(id);
    }

    @DeleteMapping("/product/delete/{id}")
    public Result<?> deleteProduct(@PathVariable Long id) {
        return adminService.deleteProduct(id);
    }

    @GetMapping("/order/list")
    public Result<?> orderList(PageDto dto) {
        return adminService.orderList(dto);
    }

    @GetMapping("/order/detail/{id}")
    public Result<?> orderDetail(@PathVariable Long id) {
        return adminService.getOrderDetail(id);
    }

    @PutMapping("/order/status/{id}")
    public Result<?> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return adminService.updateOrderStatus(id, body.get("status"));
    }
}
