package com.hdiata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdiata.pojo.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单
 * @author Lin Hua
 * @version 1.0
 * @date 12/3/2022 上午10:06
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id查询菜单
     * @param userInfoId 用户信息id
     * @return 菜单列表
     */
    List<Menu> listMenusByUserInfoId(@Param("userInfoId") Integer userInfoId);

}
