package com.hdiata.controller;

import com.hdiata.common.annotation.OptLog;
import com.hdiata.pojo.dto.LabelOptionDTO;
import com.hdiata.pojo.dto.MenuDTO;
import com.hdiata.pojo.dto.UserMenuDTO;
import com.hdiata.pojo.vo.*;
import com.hdiata.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.hdiata.common.constant.OptTypeConst.UPDATE;

/**
 * 菜单控制器
 * @author Lin Hua
 * @version 1.0
 * @date 12/3/2022 上午10:11
 */
@Api(tags = "菜单模块")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询菜单列表
     * @param condition 条件
     * @return {@link Result<MenuDTO>} 菜单列表
     */
    @ApiOperation(value = "查看菜单列表")
    @GetMapping("/admin/menus")
    public Result<List<MenuDTO>> listMenus(ConditionVO condition) {
        return Result.success(menuService.listMenus(condition));
    }

    /**
     * 修改菜单隐藏状态
     * @param menuHidden 菜单隐藏信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改菜单隐藏状态")
    @PutMapping("/admin/menus/hidden")
    public Result<?> updateMenuHidden(@RequestBody @Valid MenuHiddenVO menuHidden) {
        menuService.updateMenuHidden(menuHidden);
        return Result.success();
    }

    /**
     * 新增或修改菜单
     * @param menuVO 菜单
     * @return {@link Result<>}
     */
    @ApiOperation(value = "新增或修改菜单")
    @PostMapping("/admin/menus")
    public Result<?> saveOrUpdateMenu(@Valid @RequestBody MenuVO menuVO) {
        menuService.saveOrUpdateMenu(menuVO);
        return Result.success();
    }

    /**
     * 删除菜单
     * @param menuId 菜单id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/admin/menus/{menuId}")
    public Result<?> deleteMenu(@PathVariable("menuId") Integer menuId){
        menuService.deleteMenu(menuId);
        return Result.success();
    }

    /**
     * 查看角色菜单选项
     * @return {@link Result<LabelOptionDTO>} 查看角色菜单选项
     */
    @ApiOperation(value = "查看角色菜单选项")
    @GetMapping("/admin/role/menus")
    public Result<List<LabelOptionDTO>> listMenuOptions() {
        return Result.success(menuService.listMenuOptions());
    }

    /**
     * 查看当前用户菜单
     * @return {@link Result<UserMenuDTO>} 菜单列表
     */
    @ApiOperation(value = "查看当前用户菜单")
    @GetMapping("/admin/user/menus")
    public Result<List<UserMenuDTO>> listUserMenus() {
        return Result.success(menuService.listUserMenus());
    }

}
