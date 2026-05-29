package com.lvtu.controller;

import com.lvtu.common.Result;
import com.lvtu.dto.FavoriteDto;
import com.lvtu.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(required = false) String type,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return favoriteService.list(page, pageSize, type);
    }

    @PostMapping("/add")
    public Result<?> add(@RequestBody FavoriteDto dto) {
        return favoriteService.add(dto);
    }

    @DeleteMapping("/remove")
    public Result<?> remove(@RequestBody FavoriteDto dto) {
        return favoriteService.remove(dto);
    }
}
