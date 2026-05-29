package com.lvtu.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class FavoriteDto {
    @NotBlank
    private String targetType;

    @NotNull
    private Long targetId;
}
