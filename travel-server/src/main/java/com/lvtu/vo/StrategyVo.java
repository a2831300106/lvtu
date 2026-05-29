package com.lvtu.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class StrategyVo {
    private Long id;
    private String title;
    private String author;
    private String avatar;
    private String dest;
    private String cover;
    private Integer likes;
    private Integer comments;
    private List<String> tags;
    private LocalDateTime publishTime;
}
