package com.hdiata.controller;

import com.hdiata.common.annotation.OptLog;
import com.hdiata.pojo.dto.UserOnlineDTO;
import com.hdiata.pojo.vo.*;
import com.hdiata.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.hdiata.common.constant.OptTypeConst.UPDATE;

/**
 * 用户信息控制器
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/28 15:06
 */
@Api(tags = "用户信息模块")
@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取用户昵称
     * @param userId 用户id
     * @return {@link Result<String>} 用户昵称
     */
    @ApiOperation(value = "获取用户昵称")
    @GetMapping("/users/username/{userId}")
    public Result<String> getUsername(@PathVariable("userId") Integer userId) {
        return Result.success(userInfoService.getUsername(userId));
    }

    /**
     * 更新用户信息
     * @param userInfoVO 用户信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "更新用户信息")
    @PutMapping("/users/info")
    public Result<?> updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return Result.success();
    }

    /**
     * 更新用户头像
     * @param file 文件
     * @return {@link Result<String>} 头像地址
     */
    @ApiOperation(value = "更新用户头像")
    @ApiImplicitParam(name = "file", value = "用户头像", required = true, dataType = "MultipartFile")
    @PostMapping("/users/avatar")
    public Result<String> updateUserAvatar(MultipartFile file) {
        return Result.success(userInfoService.updateUserAvatar(file));
    }

    /**
     * 绑定用户邮箱
     * @param emailVO 邮箱信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "绑定用户邮箱")
    @PostMapping("/users/email")
    public Result<?> saveUserEmail(@Valid @RequestBody EmailVO emailVO) {
        userInfoService.saveUserEmail(emailVO);
        return Result.success();
    }

    /**
     * 修改用户角色
     * @param userRole 用户角色信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户角色")
    @PutMapping("/admin/users/role")
    public Result<?> updateUserRole(@Valid @RequestBody UserRoleVO userRole) {
        userInfoService.updateUserRole(userRole);
        return Result.success();
    }

    /**
     * 修改用户禁用状态
     * @param userDisable 用户禁用信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户禁用状态")
    @PutMapping("/admin/users/disable")
    public Result<?> updateUserDisable(@Valid @RequestBody UserDisableVO userDisable) {
        userInfoService.updateUserDisable(userDisable);
        return Result.success();
    }

    /**
     * 查看在线用户
     * @param condition 条件
     * @return {@link Result<UserOnlineDTO>} 在线用户列表
     */
    @ApiOperation(value = "查看在线用户")
    @GetMapping("/admin/users/online")
    public Result<PageResult<UserOnlineDTO>> listOnlineUsers(ConditionVO condition) {
        return Result.success(userInfoService.listOnlineUsers(condition));
    }

    /**
     * 下线用户
     * @param userInfoId 用户信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "下线用户")
    @DeleteMapping("/admin/users/{userInfoId}/online")
    public Result<?> removeOnlineUser(@PathVariable("userInfoId") Integer userInfoId) {
        userInfoService.removeOnlineUser(userInfoId);
        return Result.success();
    }

}
