package com.hdiata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdiata.pojo.dto.ResourceRoleDTO;
import com.hdiata.pojo.dto.RoleDTO;
import com.hdiata.pojo.entity.Role;
import com.hdiata.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:42
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询路由角色列表
     * @return 角色标签
     */
    List<ResourceRoleDTO> listResourceRoles();

    /**
     * 查询角色列表
     * @param current 页码
     * @param size 大小
     * @param condition 查询条件
     * @return 角色列表
     */
    List<RoleDTO> listRoles(@Param("current") Long current,
                            @Param("size") Long size,
                            @Param("condition") ConditionVO condition);

    /**
     * 根据用户id获取角色列表
     * @param userInfoId 用户id
     * @return 角色标签
     */
    List<String> listRolesByUserInfoId(@Param("userInfoId") Integer userInfoId);
}
