package com.hdiata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdiata.pojo.dto.UserBackDTO;
import com.hdiata.pojo.entity.UserAuth;
import com.hdiata.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户账号
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:46
 */
@Repository
public interface UserAuthMapper extends BaseMapper<UserAuth> {

    /**
     * 查询后台用户数量
     * @param condition 查询条件
     * @return 用户数量
     */
    Integer countUser(@Param("condition") ConditionVO condition);

    /**
     * 查询后台用户列表
     * @param current 页码
     * @param size 大小
     * @param condition 查询条件
     * @return {@link List<UserBackDTO>} 用户列表
     */
    List<UserBackDTO> listUsers(@Param("current") Long current,
                                @Param("size") Long size,
                                @Param("condition") ConditionVO condition);
}
