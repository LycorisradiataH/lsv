package com.hdiata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.common.exception.ServiceException;
import com.hdiata.mapper.CategoryMapper;
import com.hdiata.mapper.VideoMapper;
import com.hdiata.pojo.dto.CategoryBackDTO;
import com.hdiata.pojo.dto.CategoryDTO;
import com.hdiata.pojo.dto.CategoryOptionDTO;
import com.hdiata.pojo.entity.Category;
import com.hdiata.pojo.entity.Video;
import com.hdiata.pojo.vo.CategoryVO;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.service.CategoryService;
import com.hdiata.util.BeanCopyUtils;
import com.hdiata.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 分类服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 15:40
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public PageResult<CategoryDTO> listCategories() {
        return new PageResult<>(categoryMapper.listCategoryDTO(),
                categoryMapper.selectCount(null));
    }

    @Override
    public PageResult<CategoryBackDTO> listBackCategories(ConditionVO condition) {
        // 查询分类数量
        Integer count = categoryMapper.selectCount(new LambdaQueryWrapper<Category>()
                .like(StringUtils.isNotBlank(condition.getKeywords()),
                        Category::getCategoryName, condition.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询分类列表
        List<CategoryBackDTO> categoryBackList =
                categoryMapper.listCategoryBack(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(categoryBackList, count);
    }

    @Override
    public List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO condition) {
        // 搜索分类
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<Category>()
                .like(StringUtils.isNotBlank(condition.getKeywords()),
                        Category::getCategoryName, condition.getKeywords())
                .orderByDesc(Category::getId));
        return BeanCopyUtils.copyList(categoryList, CategoryOptionDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateCategory(CategoryVO categoryVO) {
        // 判断分类名重复
        Category existCategory = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, categoryVO.getCategoryName()));
        if (Objects.nonNull(existCategory) && !existCategory.getId().equals(categoryVO.getId())) {
            throw new ServiceException("分类名已存在");
        }
        Category category = Category.builder()
                .id(categoryVO.getId())
                .categoryName(categoryVO.getCategoryName())
                .build();
        this.saveOrUpdate(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategories(List<Integer> categoryIdList) {
        // 查询分类id下是否有视频
        Integer count = videoMapper.selectCount(new LambdaQueryWrapper<Video>()
                .in(Video::getCategoryId, categoryIdList));
        if (count > 0) {
            throw new ServiceException("删除失败，该分类下存在视频");
        }
        categoryMapper.deleteBatchIds(categoryIdList);
    }

}
