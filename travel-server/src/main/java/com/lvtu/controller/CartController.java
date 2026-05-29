package com.lvtu.controller;

import com.lvtu.common.Result;
import com.lvtu.dto.CartDto;
import com.lvtu.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody CartDto dto) {
        return cartService.addItem(dto);
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody CartDto dto) {
        return cartService.updateItem(dto);
    }

    @DeleteMapping("/remove")
    public Result<?> remove(@RequestParam Long productId,
                            @RequestParam String skuType) {
        return cartService.removeItem(productId, skuType);
    }

    @GetMapping("/list")
    public Result<?> list() {
        return cartService.list();
    }

    @DeleteMapping("/clear")
    public Result<?> clear() {
        return cartService.clear();
    }
}
