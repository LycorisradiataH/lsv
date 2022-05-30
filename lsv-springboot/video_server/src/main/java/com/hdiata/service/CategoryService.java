package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.CategoryBackDTO;
import com.hdiata.pojo.dto.CategoryDTO;
import com.hdiata.pojo.dto.CategoryOptionDTO;
import com.hdiata.pojo.entity.Category;
import com.hdiata.pojo.vo.CategoryVO;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;

import java.util.List;

/**
 * 分类服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 15:40
 */
public interface CategoryService extends IService<Category> {

    /**
     * 查询分类列表
     * @return
     */
    PageResult<CategoryDTO> listCategories();

    /**
     * 查询后台分类
     * @param condition 查询条件
     * @return {@link PageResult<CategoryBackDTO>} 后台分类
     */
    PageResult<CategoryBackDTO> listBackCategories(ConditionVO condition);

    /**
     * 搜索视频分类
     * @param condition 查询条件
     * @return {@link List<CategoryOptionDTO>} 分类列表
     */
    List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO condition);

    /**
     * 添加或修改分类
     * @param categoryVO 分类
     */
    void saveOrUpdateCategory(CategoryVO categoryVO);

    /**
     * 删除分类
     * @param categoryIdList 分类id集合
     */
    void deleteCategories(List<Integer> categoryIdList);

}
