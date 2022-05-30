package com.hdiata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.mapper.RoleMenuMapper;
import com.hdiata.pojo.entity.RoleMenu;
import com.hdiata.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色菜单服务
 * @author Lin Hua
 * @version 1.0
 * @date 12/3/2022 上午10:11
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
}
