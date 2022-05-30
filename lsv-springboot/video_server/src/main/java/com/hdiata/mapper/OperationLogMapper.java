package com.hdiata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdiata.pojo.entity.OperationLog;
import org.springframework.stereotype.Repository;

/**
 * 操作日志
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/2 18:46
 */
@Repository
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
