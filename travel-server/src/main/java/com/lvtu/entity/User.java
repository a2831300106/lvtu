package com.lvtu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lvtu.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
public class User extends BaseEntity {
    private String phone;
    private String password;
    private String nickname;
    private String avatar;

    @TableField("level")
    private String level;

    private LocalDateTime memberSince;

    private Integer status;
}
