package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 20:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色标签
     */
    private String roleLabel;

    /**
     * 是否禁用
     */
    private Integer isDisable;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 资源id列表
     */
    private List<Long> resourceIdList;

    /**
     * 菜单id列表
     */
    private List<Long> menuIdList;

}
