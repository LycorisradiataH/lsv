package com.hdiata.controller;

import com.hdiata.common.annotation.OptLog;
import com.hdiata.pojo.dto.RoleDTO;
import com.hdiata.pojo.dto.UserRoleDTO;
import com.hdiata.pojo.vo.*;
import com.hdiata.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.hdiata.common.constant.OptTypeConst.*;

/**
 * 角色控制器
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 20:43
 */
@Api(tags = "角色模块")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询角色列表
     * @param condition 条件
     * @return {@link Result<RoleDTO>} 角色列表
     */
    @ApiOperation(value = "查询角色列表")
    @GetMapping("/admin/roles")
    public Result<PageResult<RoleDTO>> listRoles(ConditionVO condition) {
        return Result.success(roleService.listRoles(condition));
    }

    /**
     * 查询用户角色选项
     * @return {@link Result<UserRoleDTO>} 用户角色选项
     */
    @ApiOperation(value = "查询用户角色选项")
    @GetMapping("/admin/users/role")
    public Result<List<UserRoleDTO>> listUserRoles() {
        return Result.success(roleService.listUserRoles());
    }

    /**
     * 添加或更新角色
     * @param roleVO 角色信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新角色")
    @PostMapping("/admin/role")
    public Result<?> saveOrUpdateRole(@RequestBody @Valid RoleVO roleVO) {
        roleService.saveOrUpdateRole(roleVO);
        return Result.success();
    }

    /**
     * 修改角色禁用状态
     * @param roleDisable 角色禁用信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改角色禁用状态")
    @PutMapping("/admin/roles/disable")
    public Result<?> updateRoleDisable(@RequestBody @Valid RoleDisableVO roleDisable) {
        roleService.updateRoleDisable(roleDisable);
        return Result.success();
    }

    /**
     * 删除角色
     * @param roleIdList 角色id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/admin/roles")
    public Result<?> deleteRoles(@RequestBody List<Integer> roleIdList) {
        roleService.deleteRoles(roleIdList);
        return Result.success();
    }

}
