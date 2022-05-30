package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.UserAreaDTO;
import com.hdiata.pojo.dto.UserBackDTO;
import com.hdiata.pojo.entity.UserAuth;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.PasswordVO;
import com.hdiata.pojo.vo.UserVO;

import java.util.List;

/**
 * 用户账号服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:47
 */
public interface UserAuthService extends IService<UserAuth> {

    /**
     * 发送邮箱验证码
     * @param username 邮箱号
     */
    void sendCode(String username);

    /**
     * 用户注册
     * @param user 用户对象
     */
    void register(UserVO user);

    /**
     * 修改密码
     * @param user 用户对象
     */
    void updatePassword(UserVO user);

    /**
     * 查询后台用户列表
     * @param condition 查询条件
     * @return 用户列表
     */
    PageResult<UserBackDTO> listUserBack(ConditionVO condition);

    /**
     * 获取用户区域分布
     * @param condition 查询条件
     * @return {@link List<UserAreaDTO>} 用户区域分布
     */
    List<UserAreaDTO> listUserAreas(ConditionVO condition);

    /**
     * 修改管理员密码
     * @param passwordVO 密码对象
     */
    void updateAdminPassword(PasswordVO passwordVO);

}
