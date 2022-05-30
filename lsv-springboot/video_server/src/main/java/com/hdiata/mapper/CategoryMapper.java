package com.hdiata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdiata.pojo.dto.CategoryBackDTO;
import com.hdiata.pojo.dto.CategoryDTO;
import com.hdiata.pojo.entity.Category;
import com.hdiata.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 15:38
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询分类和对应视频数量
     * @return 分类列表
     */
    List<CategoryDTO> listCategoryDTO();

    /**
     * 查询后台分类列表
     * @param current 页码
     * @param size 大小
     * @param condition 查询条件
     * @return {@link List<CategoryBackDTO>} 分类列表
     */
    List<CategoryBackDTO> listCategoryBack(@Param("current") Long current,
                                           @Param("size") Long size,
                                           @Param("condition") ConditionVO condition);
}
