package com.lvtu.service;

import com.lvtu.common.Result;
import com.lvtu.vo.HomeVo;

public interface HomeService {
    Result<HomeVo> getHomeData();
}
