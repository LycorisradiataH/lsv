package com.hdiata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.mapper.UserRoleMapper;
import com.hdiata.pojo.entity.UserRole;
import com.hdiata.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:51
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
