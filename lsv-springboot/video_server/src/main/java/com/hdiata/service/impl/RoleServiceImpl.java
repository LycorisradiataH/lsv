package com.hdiata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.common.constant.CommonConst;
import com.hdiata.common.exception.ServiceException;
import com.hdiata.common.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.hdiata.mapper.RoleMapper;
import com.hdiata.mapper.UserRoleMapper;
import com.hdiata.pojo.dto.RoleDTO;
import com.hdiata.pojo.dto.UserRoleDTO;
import com.hdiata.pojo.entity.Role;
import com.hdiata.pojo.entity.RoleMenu;
import com.hdiata.pojo.entity.RoleResource;
import com.hdiata.pojo.entity.UserRole;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.RoleDisableVO;
import com.hdiata.pojo.vo.RoleVO;
import com.hdiata.service.RoleMenuService;
import com.hdiata.service.RoleResourceService;
import com.hdiata.service.RoleService;
import com.hdiata.util.BeanCopyUtils;
import com.hdiata.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:43
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @Override
    public List<UserRoleDTO> listUserRoles() {
        // 查询角色列表
        List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<Role>()
                .select(Role::getId, Role::getRoleName));
        return BeanCopyUtils.copyList(roleList, UserRoleDTO.class);
    }

    @Override
    public PageResult<RoleDTO> listRoles(ConditionVO condition) {
        // 查询角色列表
        List<RoleDTO> roleDTOList =
                roleMapper.listRoles(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        // 查询总量
        Integer count = roleMapper.selectCount(new LambdaQueryWrapper<Role>()
                .like(StringUtils.isNotBlank(condition.getKeywords()),
                        Role::getRoleName, condition.getKeywords()));
        return new PageResult<>(roleDTOList, count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateRole(RoleVO roleVO) {
        // 判断角色名重复
        Role existRole = roleMapper.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getRoleName, roleVO.getRoleName()));
        if (Objects.nonNull(existRole) && !existRole.getId().equals(roleVO.getId())) {
            throw new ServiceException("角色名已存在");
        }
        // 保存或更新角色信息
        Role role = Role.builder()
                .id(roleVO.getId())
                .roleName(roleVO.getRoleName())
                .roleLabel(roleVO.getRoleLabel())
                .isDisable(CommonConst.FALSE)
                .build();
        this.saveOrUpdate(role);
        // 更新角色资源关系
        if (CollectionUtils.isNotEmpty(roleVO.getResourceIdList())) {
            if (Objects.nonNull(roleVO.getId())) {
                roleResourceService.remove(new LambdaQueryWrapper<RoleResource>()
                        .eq(RoleResource::getRoleId, roleVO.getId()));
            }
            List<RoleResource> roleResourceList = roleVO.getResourceIdList().stream()
                    .map(resourceId -> RoleResource.builder()
                            .roleId(role.getId())
                            .resourceId(resourceId)
                            .build())
                    .collect(Collectors.toList());
            roleResourceService.saveBatch(roleResourceList);
            // 重新加载角色资源信息
            filterInvocationSecurityMetadataSource.clearDataSource();
        }
        // 更新角色菜单关系
        if (CollectionUtils.isNotEmpty(roleVO.getMenuIdList())) {
            if (Objects.nonNull(roleVO.getId())) {
                roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId,
                        roleVO.getId()));
            }
            List<RoleMenu> roleMenuList = roleVO.getMenuIdList().stream()
                    .map(menuId -> RoleMenu.builder()
                            .roleId(role.getId())
                            .menuId(menuId)
                            .build())
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        // 判断角色下是否有用户
        Integer count = userRoleMapper.selectCount(new LambdaQueryWrapper<UserRole>()
                .in(UserRole::getRoleId, roleIdList));
        if (count > 0) {
            throw new ServiceException("该角色下存在用户");
        }
        roleMapper.deleteBatchIds(roleIdList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRoleDisable(RoleDisableVO roleDisable) {
        Role role = Role.builder()
                .id(roleDisable.getId())
                .isDisable(roleDisable.getIsDisable())
                .build();
        roleMapper.updateById(role);
        filterInvocationSecurityMetadataSource.clearDataSource();
    }

}
