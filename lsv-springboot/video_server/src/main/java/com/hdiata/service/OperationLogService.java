package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.OperationLogDTO;
import com.hdiata.pojo.entity.OperationLog;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;

/**
 * 操作日志服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/2 18:43
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 查询日志列表
     * @param condition 查询条件
     * @return 日志列表
     */
    PageResult<OperationLogDTO> listOperationLogs(ConditionVO condition);

}
