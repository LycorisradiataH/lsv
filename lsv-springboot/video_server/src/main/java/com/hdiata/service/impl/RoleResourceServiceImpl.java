package com.hdiata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.mapper.RoleResourceMapper;
import com.hdiata.pojo.entity.RoleResource;
import com.hdiata.service.RoleResourceService;
import org.springframework.stereotype.Service;

/**
 * 角色资源服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 21:06
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {
}
