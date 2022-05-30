package com.hdiata.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.common.constant.CommonConst;
import com.hdiata.common.enums.LoginTypeEnum;
import com.hdiata.common.enums.RoleEnum;
import com.hdiata.common.exception.ServiceException;
import com.hdiata.mapper.UserAuthMapper;
import com.hdiata.mapper.UserInfoMapper;
import com.hdiata.mapper.UserRoleMapper;
import com.hdiata.pojo.dto.EmailDTO;
import com.hdiata.pojo.dto.UserAreaDTO;
import com.hdiata.pojo.dto.UserBackDTO;
import com.hdiata.pojo.entity.UserAuth;
import com.hdiata.pojo.entity.UserInfo;
import com.hdiata.pojo.entity.UserRole;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.PasswordVO;
import com.hdiata.pojo.vo.UserVO;
import com.hdiata.service.UserAuthService;
import com.hdiata.service.VideoInfoService;
import com.hdiata.util.PageUtils;
import com.hdiata.util.RedisUtils;
import com.hdiata.util.UserUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hdiata.common.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.hdiata.common.constant.RedisPrefixConst.*;
import static com.hdiata.common.enums.UserAreaTypeEnum.getUserAreaType;
import static com.hdiata.util.CommonUtils.checkEmail;
import static com.hdiata.util.CommonUtils.getRandomCode;

/**
 * 用户账号服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:47
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private VideoInfoService videoInfoService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void sendCode(String username) {
        // 校验账号是否合法
        if (!checkEmail(username)) {
            throw new ServiceException("请输入正确邮箱");
        }
        // 生成六位随机验证码发送
        String code = getRandomCode();
        // 发送验证码
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject("lsv的验证码")
                .content("您的验证码为: \n\n"
                        + "<h1><span style='background-color: #000;color: #fff;padding: 5px'>"
                        + code + "</span></h1>"
                        + "\n\n有效期为<font color='red'>15</font>分钟，请尽快填写喔！")
                .build();
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*",
                new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        // 将验证码存入redis，设置过期时间为15分钟
        redisUtils.set(USER_CODE_KEY + username, code, CODE_EXPIRE_TIME);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserVO user) {
        // 校验账号是否合法
        if (checkUser(user)) {
            throw new ServiceException("邮箱已被注册！");
        }
        // 新增用户信息
        UserInfo userInfo = UserInfo.builder()
                .email(user.getUsername())
                .nickname(CommonConst.DEFAULT_NICKNAME + IdWorker.getId())
                .avatar(videoInfoService.getWebsiteConfig().getUserAvatar())
                .build();
        userInfoMapper.insert(userInfo);
        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
        // 新增用户账号
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(user.getUsername())
                .password(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .loginType(LoginTypeEnum.EMAIL.getType())
                .build();
        userAuthMapper.insert(userAuth);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePassword(UserVO user) {
        // 校验账号是否合法
        if (!checkUser(user)) {
            throw new ServiceException("邮箱尚未注册！");
        }
        // 根据用户名修改密码
        userAuthMapper.update(new UserAuth(), new LambdaUpdateWrapper<UserAuth>()
                .set(UserAuth::getPassword, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .eq(UserAuth::getUsername, user.getUsername()));
    }

    @Override
    public PageResult<UserBackDTO> listUserBack(ConditionVO condition) {
        // 获取后台用户数量
        Integer count = userAuthMapper.countUser(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 获取后台用户列表
        List<UserBackDTO> userBackDTOList =
                userAuthMapper.listUsers(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(userBackDTOList, count);
    }

    @Override
    public List<UserAreaDTO> listUserAreas(ConditionVO condition) {
        List<UserAreaDTO> userAreaDTOList = new ArrayList<>();
        switch (Objects.requireNonNull(getUserAreaType(condition.getType()))) {
            case USER:
                // 查询注册用户区域分布
                Object userArea = redisUtils.get(USER_AREA);
                if (Objects.nonNull(userArea)) {
                    userAreaDTOList = JSON.parseObject(userArea.toString(), List.class);
                }
                return userAreaDTOList;
            case VISITOR:
                // 查询游客区域分布
                Map<Object, Object> visitorArea = redisUtils.hmGet(VISITOR_AREA);
                if (Objects.nonNull(visitorArea)) {
                    userAreaDTOList = visitorArea.entrySet().stream()
                            .map(item -> UserAreaDTO.builder()
                                    .name(String.valueOf(item.getKey()))
                                    .value(Long.valueOf(item.getValue().toString()))
                                    .build())
                            .collect(Collectors.toList());
                }
                return userAreaDTOList;
            default:
                break;
        }
        return userAreaDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAdminPassword(PasswordVO passwordVO) {
        // 查询旧密码是否正确
        UserAuth user = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getId, UserUtils.getLoginUser().getId()));
        // 正确则修改密码，错误则提示不正确
        if (Objects.nonNull(user) && BCrypt.checkpw(passwordVO.getOldPassword(), user.getPassword())) {
            UserAuth userAuth = UserAuth.builder()
                    .id(UserUtils.getLoginUser().getId())
                    .password(BCrypt.hashpw(passwordVO.getNewPassword(), BCrypt.gensalt()))
                    .build();
            userAuthMapper.updateById(userAuth);
        } else {
            throw new ServiceException("旧密码不正确");
        }
    }

    /**
     * 校验用户数据是否合法
     * @param user 用户数据
     * @return 结果
     */
    private Boolean checkUser(UserVO user) {
        String code = (String) redisUtils.get(USER_CODE_KEY + user.getUsername());
        if (!user.getCode().equalsIgnoreCase(code)) {
            throw new ServiceException("验证码错误！");
        }
        // 查询用户名是否存在
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }

}
