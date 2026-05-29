package com.lvtu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvtu.common.PageResult;
import com.lvtu.common.Result;
import com.lvtu.common.SecurityUtils;
import com.lvtu.dto.StrategyPublishDto;
import com.lvtu.entity.Strategy;
import com.lvtu.entity.StrategyComment;
import com.lvtu.entity.User;
import com.lvtu.mapper.StrategyCommentMapper;
import com.lvtu.mapper.StrategyMapper;
import com.lvtu.mapper.UserMapper;
import com.lvtu.service.StrategyService;
import com.lvtu.vo.CommentVo;
import com.lvtu.vo.StrategyDetailVo;
import com.lvtu.vo.StrategyVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StrategyServiceImpl implements StrategyService {

    private final StrategyMapper strategyMapper;
    private final StrategyCommentMapper strategyCommentMapper;
    private final UserMapper userMapper;

    public StrategyServiceImpl(StrategyMapper strategyMapper,
                               StrategyCommentMapper strategyCommentMapper,
                               UserMapper userMapper) {
        this.strategyMapper = strategyMapper;
        this.strategyCommentMapper = strategyCommentMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Result<PageResult<StrategyVo>> list(String keyword, Integer page, Integer pageSize) {
        LambdaQueryWrapper<Strategy> wrapper = new LambdaQueryWrapper<Strategy>()
                .eq(Strategy::getStatus, 1);
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Strategy::getTitle, keyword);
        }
        wrapper.orderByDesc(Strategy::getPublishTime);
        Page<Strategy> p = new Page<>(page, pageSize);
        strategyMapper.selectPage(p, wrapper);
        List<StrategyVo> vos = p.getRecords().stream().map(this::buildStrategyVo).toList();
        PageResult<StrategyVo> result = new PageResult<>(vos, p.getTotal(), page, pageSize);
        return Result.success(result);
    }

    @Override
    public Result<StrategyDetailVo> getDetail(Long id) {
        Strategy strategy = strategyMapper.selectById(id);
        if (strategy == null) {
            return Result.error("攻略不存在");
        }
        StrategyDetailVo vo = new StrategyDetailVo();
        vo.setId(strategy.getId());
        vo.setTitle(strategy.getTitle());
        vo.setContent(strategy.getContent());
        vo.setAuthor(strategy.getAuthor());
        vo.setAvatar(strategy.getAvatar());
        vo.setDest(strategy.getDest());
        vo.setCover(strategy.getCover());
        vo.setLikes(strategy.getLikes());
        vo.setComments(strategy.getComments());
        if (strategy.getTags() != null && !strategy.getTags().isEmpty()) {
            vo.setTags(Arrays.asList(strategy.getTags().split(",")));
        }
        vo.setPublishTime(strategy.getPublishTime());
        List<StrategyComment> comments = strategyCommentMapper.selectList(
                new LambdaQueryWrapper<StrategyComment>()
                        .eq(StrategyComment::getStrategyId, id)
                        .orderByDesc(StrategyComment::getCreateTime)
        );
        List<CommentVo> commentVos = comments.stream().map(c -> {
            CommentVo cv = new CommentVo();
            cv.setId(c.getId());
            cv.setAuthor(c.getAuthor());
            cv.setAvatar(c.getAvatar());
            cv.setContent(c.getContent());
            cv.setCreateTime(c.getCreateTime());
            return cv;
        }).collect(Collectors.toList());
        vo.setCommentsList(commentVos);
        return Result.success(vo);
    }

    @Override
    public Result<Void> publish(StrategyPublishDto dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        User user = userMapper.selectById(userId);
        Strategy strategy = new Strategy();
        strategy.setTitle(dto.getTitle());
        strategy.setContent(dto.getContent());
        strategy.setDest(dto.getDest());
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            strategy.setTags(String.join(",", dto.getTags()));
        }
        strategy.setAuthor(user != null ? user.getNickname() : "匿名用户");
        strategy.setAvatar(user != null ? user.getAvatar() : null);
        strategy.setLikes(0);
        strategy.setComments(0);
        strategy.setStatus(1);
        strategy.setPublishTime(LocalDateTime.now());
        strategyMapper.insert(strategy);
        return Result.success();
    }

    @Override
    public Result<Void> like(Long id) {
        Strategy strategy = strategyMapper.selectById(id);
        if (strategy == null) {
            return Result.error("攻略不存在");
        }
        strategy.setLikes(strategy.getLikes() == null ? 1 : strategy.getLikes() + 1);
        strategyMapper.updateById(strategy);
        return Result.success();
    }

    @Override
    public Result<Void> comment(Long strategyId, String content) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return Result.error("未登录");
        }
        User user = userMapper.selectById(userId);
        StrategyComment comment = new StrategyComment();
        comment.setStrategyId(strategyId);
        comment.setUserId(userId);
        comment.setAuthor(user != null ? user.getNickname() : "匿名用户");
        comment.setAvatar(user != null ? user.getAvatar() : null);
        comment.setContent(content);
        strategyCommentMapper.insert(comment);
        Strategy strategy = strategyMapper.selectById(strategyId);
        if (strategy != null) {
            strategy.setComments(strategy.getComments() == null ? 1 : strategy.getComments() + 1);
            strategyMapper.updateById(strategy);
        }
        return Result.success();
    }

    private StrategyVo buildStrategyVo(Strategy strategy) {
        StrategyVo vo = new StrategyVo();
        vo.setId(strategy.getId());
        vo.setTitle(strategy.getTitle());
        vo.setAuthor(strategy.getAuthor());
        vo.setAvatar(strategy.getAvatar());
        vo.setDest(strategy.getDest());
        vo.setCover(strategy.getCover());
        vo.setLikes(strategy.getLikes());
        vo.setComments(strategy.getComments());
        if (strategy.getTags() != null && !strategy.getTags().isEmpty()) {
            vo.setTags(Arrays.asList(strategy.getTags().split(",")));
        }
        vo.setPublishTime(strategy.getPublishTime());
        return vo;
    }
}
