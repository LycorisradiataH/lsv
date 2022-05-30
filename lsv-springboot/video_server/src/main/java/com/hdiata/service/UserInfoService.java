package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.UserOnlineDTO;
import com.hdiata.pojo.entity.UserInfo;
import com.hdiata.pojo.vo.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:49
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 获取用户昵称
     * @param userId 用户id
     * @return 用户昵称
     */
    String getUsername(Integer userId);

    /**
     * 修改用户资料
     * @param userInfoVO 用户资料
     */
    void updateUserInfo(UserInfoVO userInfoVO);

    /**
     * 修改用户头像
     * @param file 头像图片
     * @return 头像地址
     */
    String updateUserAvatar(MultipartFile file);

    /**
     * 绑定用户邮箱
     * @param emailVO 邮箱
     */
    void saveUserEmail(EmailVO emailVO);

    /**
     * 更新用户角色
     * @param userRole 更新用户角色
     */
    void updateUserRole(UserRoleVO userRole);

    /**
     * 修改用户禁用状态
     * @param userDisable 用户禁用信息
     */
    void updateUserDisable(UserDisableVO userDisable);

    /**
     * 查看在线用户列表
     * @param condition 条件
     * @return 在线用户列表
     */
    PageResult<UserOnlineDTO> listOnlineUsers(ConditionVO condition);

    /**
     * 下线用户
     * @param userInfoId 用户信息id
     */
    void removeOnlineUser(Integer userInfoId);

}
