package com.hdiata.controller;

import com.hdiata.common.annotation.OptLog;
import com.hdiata.pojo.dto.CategoryBackDTO;
import com.hdiata.pojo.dto.CategoryDTO;
import com.hdiata.pojo.dto.CategoryOptionDTO;
import com.hdiata.pojo.vo.CategoryVO;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.Result;
import com.hdiata.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.hdiata.common.constant.OptTypeConst.REMOVE;
import static com.hdiata.common.constant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * 分类控制器
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 15:45
 */
@Api(tags = "分类模块")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查看分类列表
     * @return {@link Result<CategoryDTO>} 分类列表
     */
    @ApiOperation(value = "查看分类列表")
    @GetMapping("/categories")
    public Result<PageResult<CategoryDTO>> listCategories() {
        return Result.success(categoryService.listCategories());
    }


    /**
     * 查看后台分类列表
     * @param condition 查询条件
     * @return {@link Result<CategoryBackDTO>} 后台分类列表
     */
    @ApiOperation(value = "查看后台分类列表")
    @GetMapping("/admin/categories")
    public Result<PageResult<CategoryBackDTO>> listBackCategories(ConditionVO condition) {
        return Result.success(categoryService.listBackCategories(condition));
    }

    /**
     * 搜索视频分类
     * @param condition 查询条件
     * @return {@link Result<CategoryOptionDTO>} 分类列表
     */
    @ApiOperation(value = "搜索视频分类")
    @GetMapping("/admin/categories/search")
    public Result<List<CategoryOptionDTO>> listCategoriesBySearch(ConditionVO condition) {
        return Result.success(categoryService.listCategoriesBySearch(condition));
    }

    /**
     * 添加或修改分类
     * @param categoryVO 分类信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改分类")
    @PostMapping("/admin/categories")
    public Result<?> saveOrUpdateCategory(@Valid @RequestBody CategoryVO categoryVO) {
        categoryService.saveOrUpdateCategory(categoryVO);
        return Result.success();
    }

    /**
     * 删除分类
     * @param categoryIdList 分类id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除分类")
    @DeleteMapping("/admin/categories")
    public Result<?> deleteCategories(@RequestBody List<Integer> categoryIdList) {
        categoryService.deleteCategories(categoryIdList);
        return Result.success();
    }

}
