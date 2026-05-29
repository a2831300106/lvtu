package com.lvtu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lvtu.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("category")
public class Category extends BaseEntity {
    private String name;
    private String icon;
    private Integer sort;
}
