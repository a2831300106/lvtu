package com.lvtu.dto;

import lombok.Data;

@Data
public class PageDto {
    private String keyword;
    private String category;
    private String status;
    private String startDate;
    private String endDate;
    private Integer page = 1;
    private Integer pageSize = 10;
}
