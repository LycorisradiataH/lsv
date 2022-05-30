package com.hdiata.controller;

import com.hdiata.common.annotation.OptLog;
import com.hdiata.pojo.dto.TagBackDTO;
import com.hdiata.pojo.dto.TagDTO;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.Result;
import com.hdiata.pojo.vo.TagVO;
import com.hdiata.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.hdiata.common.constant.OptTypeConst.REMOVE;
import static com.hdiata.common.constant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * 标签控制器
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 17:03
 */
@Api(tags = "标签模块")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 查询后台标签列表
     * @param condition 查询条件
     * @return {@link Result<TagBackDTO>} 标签列表
     */
    @ApiOperation(value = "查询后台标签列表")
    @GetMapping("/admin/tags")
    public Result<PageResult<TagBackDTO>> listTagsBack(ConditionVO condition) {
        return Result.success(tagService.listTagsBack(condition));
    }

    /**
     * 搜索视频标签
     * @param condition 查询条件
     * @return {@link Result<String>} 标签列表
     */
    @ApiOperation(value = "搜索视频标签")
    @GetMapping("/admin/tags/search")
    public Result<List<TagDTO>> listTagsBySearch(ConditionVO condition) {
        return Result.success(tagService.listTagsBySearch(condition));
    }

    /**
     * 添加或修改标签
     * @param tagVO 标签信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改标签")
    @PostMapping("/admin/tags")
    public Result<?> saveOrUpdateTag(@Valid @RequestBody TagVO tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return Result.success();
    }

    /**
     * 删除标签
     * @param tagIdList 标签id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除标签")
    @DeleteMapping("/admin/tags")
    public Result<?> deleteTags(@RequestBody List<Integer> tagIdList) {
        tagService.deleteTags(tagIdList);
        return Result.success();
    }

}
