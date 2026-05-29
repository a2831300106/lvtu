package com.lvtu.controller;

import com.lvtu.common.Result;
import com.lvtu.dto.StrategyPublishDto;
import com.lvtu.service.StrategyService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/strategy")
public class StrategyController {

    private final StrategyService strategyService;

    public StrategyController(StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(required = false) String keyword,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return strategyService.list(keyword, page, pageSize);
    }

    @GetMapping("/detail/{id}")
    public Result<?> detail(@PathVariable Long id) {
        return strategyService.getDetail(id);
    }

    @PostMapping("/publish")
    public Result<?> publish(@RequestBody StrategyPublishDto dto) {
        return strategyService.publish(dto);
    }

    @PostMapping("/like/{id}")
    public Result<?> like(@PathVariable Long id) {
        return strategyService.like(id);
    }

    @PostMapping("/comment/{id}")
    public Result<?> comment(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return strategyService.comment(id, body.get("content"));
    }
}
