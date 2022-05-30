package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.LabelOptionDTO;
import com.hdiata.pojo.dto.MenuDTO;
import com.hdiata.pojo.dto.UserMenuDTO;
import com.hdiata.pojo.entity.Menu;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.MenuHiddenVO;
import com.hdiata.pojo.vo.MenuVO;

import java.util.List;

/**
 * 菜单服务
 * @author Lin Hua
 * @version 1.0
 * @date 12/3/2022 上午10:08
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查看菜单列表
     * @param condition 条件
     * @return 菜单列表
     */
    List<MenuDTO> listMenus(ConditionVO condition);

    /**
     * 修改菜单隐藏状态
     * @param menuHidden 菜单隐藏信息
     */
    void updateMenuHidden(MenuHiddenVO menuHidden);

    /**
     * 新增或修改菜单
     * @param menuVO 菜单信息
     */
    void saveOrUpdateMenu(MenuVO menuVO);

    /**
     * 删除菜单
     * @param menuId 菜单id
     */
    void deleteMenu(Integer menuId);

    /**
     * 查看角色菜单选项
     * @return 角色菜单选项
     */
    List<LabelOptionDTO> listMenuOptions();

    /**
     * 查看用户菜单
     * @return 菜单列表
     */
    List<UserMenuDTO> listUserMenus();

}
