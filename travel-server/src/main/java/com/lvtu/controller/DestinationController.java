package com.lvtu.controller;

import com.lvtu.common.Result;
import com.lvtu.service.DestinationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/destination")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam(required = false) String keyword) {
        return destinationService.list(keyword);
    }
}
