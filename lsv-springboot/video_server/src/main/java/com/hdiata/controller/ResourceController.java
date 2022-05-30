package com.hdiata.controller;

import com.hdiata.pojo.dto.LabelOptionDTO;
import com.hdiata.pojo.dto.ResourceDTO;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.ResourceVO;
import com.hdiata.pojo.vo.Result;
import com.hdiata.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 资源控制器
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 18:31
 */
@Api(tags = "资源模块")
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "导入swagger接口")
    @GetMapping("/admin/resources/import/swagger")
    public Result<?> importSwagger() {
        resourceService.importSwagger();
        return Result.success();
    }

    /**
     * 查看资源列表
     * @return {@link Result<ResourceDTO>} 资源列表
     */
    @ApiOperation(value = "查看资源列表")
    @GetMapping("/admin/resources")
    public Result<List<ResourceDTO>> listResources(ConditionVO conditionVO) {
        return Result.success(resourceService.listResources(conditionVO));
    }

    /**
     * 新增或修改资源
     * @param resourceVO 资源信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "新增或修改资源")
    @PostMapping("/admin/resources")
    public Result<?> saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.success();
    }

    /**
     * 删除资源
     * @param resourceId 资源id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除资源")
    @DeleteMapping("/admin/resources/{resourceId}")
    public Result<?> deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.success();
    }

    /**
     * 查看角色资源选项
     * @return {@link Result<LabelOptionDTO>} 角色资源选项
     */
    @ApiOperation(value = "查看角色资源选项")
    @GetMapping("/admin/role/resources")
    public Result<List<LabelOptionDTO>> listResourceOption() {
        return Result.success(resourceService.listResourceOption());
    }

}
