package com.lvtu.service;

import com.lvtu.common.Result;
import com.lvtu.vo.DestinationVo;
import java.util.List;

public interface DestinationService {
    Result<List<DestinationVo>> list(String keyword);
}
