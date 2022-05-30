package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台用户
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/28 14:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBackDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 用户信息id
     */
    private Integer userInfoId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户角色
     */
    private List<UserRoleDTO> roleList;

    /**
     * 登录类型
     */
    private Integer loginType;

    /**
     * 用户登录ip
     */
    private String ipAddress;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 用户状态
     */
    private Integer isDisable;

    /**
     * 状态
     */
    private Integer status;

}
