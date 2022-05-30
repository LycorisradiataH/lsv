package com.hdiata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdiata.pojo.dto.TagBackDTO;
import com.hdiata.pojo.entity.Tag;
import com.hdiata.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:44
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 查询后台标签列表
     * @param current 页码
     * @param size 大小
     * @param condition 查询条件
     * @return {@link List<TagBackDTO>} 标签列表
     */
    List<TagBackDTO> listTagsBack(@Param("current") Long current,
                                  @Param("size") Long size,
                                  @Param("condition") ConditionVO condition);

}
