package com.lvtu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lvtu.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("favorite")
public class Favorite extends BaseEntity {
    private Long userId;
    private String targetType;
    private Long targetId;
}
