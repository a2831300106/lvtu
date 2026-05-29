package com.lvtu.vo;

import com.lvtu.entity.Banner;
import com.lvtu.entity.Category;
import com.lvtu.entity.Product;
import lombok.Data;
import java.util.List;

@Data
public class HomeVo {
    private List<Banner> banners;
    private List<Category> categories;
    private List<DestinationVo> hotDestinations;
    private List<Product> flashDeals;
    private List<StrategyVo> travelNotes;
}
