package com.hdiata.util;

import com.hdiata.pojo.dto.UserDetailDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 用户工具类
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/2 18:58
 */
@Component
public class UserUtils {

    /**
     * 获取当前登录用户
     * @return 用户登录信息
     */
    public static UserDetailDTO getLoginUser() {
        return (UserDetailDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
