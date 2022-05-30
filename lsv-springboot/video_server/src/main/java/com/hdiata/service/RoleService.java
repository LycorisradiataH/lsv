package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.RoleDTO;
import com.hdiata.pojo.dto.UserRoleDTO;
import com.hdiata.pojo.entity.Role;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.RoleDisableVO;
import com.hdiata.pojo.vo.RoleVO;

import java.util.List;

/**
 * 角色服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:42
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取用户角色选项
     * @return 角色
     */
    List<UserRoleDTO> listUserRoles();

    /**
     * 查询角色列表
     * @param condition 查询条件
     * @return 角色列表
     */
    PageResult<RoleDTO> listRoles(ConditionVO condition);

    /**
     * 保存或更新角色
     * @param roleVO 角色
     */
    void saveOrUpdateRole(RoleVO roleVO);

    /**
     * 修改角色禁用状态
     * @param roleDisable 角色禁用信息
     */
    void updateRoleDisable(RoleDisableVO roleDisable);

    /**
     * 删除角色
     * @param roleIdList 角色id列表
     */
    void deleteRoles(List<Integer> roleIdList);

}
