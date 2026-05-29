package com.lvtu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvtu.common.Result;
import com.lvtu.entity.Banner;
import com.lvtu.entity.Category;
import com.lvtu.entity.Product;
import com.lvtu.entity.Strategy;
import com.lvtu.mapper.BannerMapper;
import com.lvtu.mapper.CategoryMapper;
import com.lvtu.mapper.ProductMapper;
import com.lvtu.mapper.StrategyMapper;
import com.lvtu.service.HomeService;
import com.lvtu.vo.DestinationVo;
import com.lvtu.vo.HomeVo;
import com.lvtu.vo.StrategyVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HomeServiceImpl implements HomeService {

    private final BannerMapper bannerMapper;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    private final StrategyMapper strategyMapper;

    public HomeServiceImpl(BannerMapper bannerMapper, CategoryMapper categoryMapper,
                           ProductMapper productMapper, StrategyMapper strategyMapper) {
        this.bannerMapper = bannerMapper;
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
        this.strategyMapper = strategyMapper;
    }

    @Override
    public Result<HomeVo> getHomeData() {
        HomeVo homeVo = new HomeVo();
        List<Banner> banners = bannerMapper.selectList(
                new LambdaQueryWrapper<Banner>()
                        .eq(Banner::getStatus, 1)
                        .orderByAsc(Banner::getSort)
        );
        homeVo.setBanners(banners);
        List<Category> categories = categoryMapper.selectList(
                new LambdaQueryWrapper<Category>()
                        .orderByAsc(Category::getSort)
        );
        homeVo.setCategories(categories);
        List<Product> hotProducts = productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getStatus, 1)
                        .orderByDesc(Product::getSales)
                        .last("LIMIT 6")
        );
        List<DestinationVo> destinations = hotProducts.stream().map(p -> {
            DestinationVo d = new DestinationVo();
            d.setId(p.getId());
            d.setName(p.getName());
            d.setImage(p.getImage());
            d.setDescription(p.getDescription());
            d.setRating(5.0);
            return d;
        }).collect(Collectors.toList());
        homeVo.setHotDestinations(destinations);
        List<Product> flashDeals = productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getStatus, 1)
                        .orderByDesc(Product::getSales)
                        .last("LIMIT 4")
        );
        homeVo.setFlashDeals(flashDeals);
        List<Strategy> strategies = strategyMapper.selectList(
                new LambdaQueryWrapper<Strategy>()
                        .eq(Strategy::getStatus, 1)
                        .orderByDesc(Strategy::getPublishTime)
                        .last("LIMIT 4")
        );
        List<StrategyVo> strategyVos = strategies.stream().map(s -> {
            StrategyVo vo = new StrategyVo();
            vo.setId(s.getId());
            vo.setTitle(s.getTitle());
            vo.setAuthor(s.getAuthor());
            vo.setAvatar(s.getAvatar());
            vo.setDest(s.getDest());
            vo.setCover(s.getCover());
            vo.setLikes(s.getLikes());
            vo.setComments(s.getComments());
            if (s.getTags() != null && !s.getTags().isEmpty()) {
                vo.setTags(Arrays.asList(s.getTags().split(",")));
            }
            vo.setPublishTime(s.getPublishTime());
            return vo;
        }).collect(Collectors.toList());
        homeVo.setTravelNotes(strategyVos);
        return Result.success(homeVo);
    }
}
