package com.lvtu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lvtu.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("order_comment")
public class OrderComment extends BaseEntity {
    private Long orderId;
    private Long userId;
    private Long productId;
    private Integer rating;
    private String content;
}
