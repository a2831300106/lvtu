package com.lvtu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lvtu.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("strategy_comment")
public class StrategyComment extends BaseEntity {
    private Long strategyId;
    private Long userId;
    private String author;
    private String avatar;
    private String content;
    private Long parentId;
}
