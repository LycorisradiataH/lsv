package com.hdiata.common.handler;

import com.alibaba.fastjson.JSON;
import com.hdiata.mapper.UserAuthMapper;
import com.hdiata.pojo.dto.UserInfoDTO;
import com.hdiata.pojo.entity.UserAuth;
import com.hdiata.pojo.vo.Result;
import com.hdiata.util.BeanCopyUtils;
import com.hdiata.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理
 * @author Lin Hua
 * @version 1.0
 * @date 12/3/2022 上午11:05
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // 返回登录信息
        UserInfoDTO userLoginDTO = BeanCopyUtils.copyObject(UserUtils.getLoginUser(), UserInfoDTO.class);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.success(userLoginDTO)));
        // 更新用户ip，最近登录时间
        updateUserInfo();
    }

    /**
     * 更新用户信息
     */
    @Async
    public void updateUserInfo() {
        UserAuth userAuth = UserAuth.builder()
                .id(UserUtils.getLoginUser().getId())
                .ipAddress(UserUtils.getLoginUser().getIpAddress())
                .ipSource(UserUtils.getLoginUser().getIpSource())
                .lastLoginTime(UserUtils.getLoginUser().getLastLoginTime())
                .build();
        userAuthMapper.updateById(userAuth);
    }

}
