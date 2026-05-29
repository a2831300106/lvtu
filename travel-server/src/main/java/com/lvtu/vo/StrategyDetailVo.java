package com.lvtu.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class StrategyDetailVo extends StrategyVo {
    private String content;
    private List<CommentVo> commentsList;
}
