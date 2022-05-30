package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.UniqueViewDTO;
import com.hdiata.pojo.entity.UniqueView;

import java.util.List;

/**
 * 用户量统计服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/26 16:13
 */
public interface UniqueViewService extends IService<UniqueView> {

    /**
     * 获取7天用户量统计
     * @return 用户量
     */
    List<UniqueViewDTO> listUniqueView();

}
