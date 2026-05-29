package com.lvtu.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
public class StrategyPublishDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String dest;

    private List<String> tags;
}
