package com.lvtu.service;

import com.lvtu.common.PageResult;
import com.lvtu.common.Result;
import com.lvtu.dto.StrategyPublishDto;
import com.lvtu.vo.StrategyDetailVo;
import com.lvtu.vo.StrategyVo;

public interface StrategyService {
    Result<PageResult<StrategyVo>> list(String keyword, Integer page, Integer pageSize);
    Result<StrategyDetailVo> getDetail(Long id);
    Result<Void> publish(StrategyPublishDto dto);
    Result<Void> like(Long id);
    Result<Void> comment(Long strategyId, String content);
}
