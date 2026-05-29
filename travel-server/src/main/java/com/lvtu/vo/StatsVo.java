package com.lvtu.vo;

import lombok.Data;
import java.util.List;

@Data
public class StatsVo {
    private Long totalUsers;
    private Long totalProducts;
    private Long totalOrders;
    private Long todayOrders;
    private List<Long> orderTrend;
    private List<String> trendLabels;
    private List<CategoryStatVo> categoryStats;
}
