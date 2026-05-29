package com.lvtu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lvtu.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("strategy")
public class Strategy extends BaseEntity {
    private String title;
    private String content;
    private String author;
    private String avatar;
    private String dest;
    private String cover;
    private Integer likes;
    private Integer comments;
    private String tags;
    private Integer status;
    private LocalDateTime publishTime;
}
