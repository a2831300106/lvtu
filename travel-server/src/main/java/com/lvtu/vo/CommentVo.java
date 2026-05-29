package com.lvtu.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentVo {
    private Long id;
    private String author;
    private String avatar;
    private String content;
    private LocalDateTime createTime;
}
