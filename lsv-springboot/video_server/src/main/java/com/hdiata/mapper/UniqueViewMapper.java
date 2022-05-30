package com.hdiata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdiata.pojo.dto.UniqueViewDTO;
import com.hdiata.pojo.entity.UniqueView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 用户量统计
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/26 16:20
 */
@Repository
public interface UniqueViewMapper extends BaseMapper<UniqueView> {

    /**
     * 获取七天用户访问量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 用户访问量
     */
    List<UniqueViewDTO> listUniqueView(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
