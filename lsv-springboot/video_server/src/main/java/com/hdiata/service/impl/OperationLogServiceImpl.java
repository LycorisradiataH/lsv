package com.hdiata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.mapper.OperationLogMapper;
import com.hdiata.pojo.dto.OperationLogDTO;
import com.hdiata.pojo.entity.OperationLog;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.service.OperationLogService;
import com.hdiata.util.BeanCopyUtils;
import com.hdiata.util.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/2 18:45
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Override
    public PageResult<OperationLogDTO> listOperationLogs(ConditionVO condition) {
        Page<OperationLog> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        // 查询日志列表
        Page<OperationLog> operationLogPage = this.page(page, new LambdaQueryWrapper<OperationLog>()
                .like(StringUtils.isNotBlank(condition.getKeywords()),
                        OperationLog::getOptModule, condition.getKeywords())
                .or()
                .like(StringUtils.isNotBlank(condition.getKeywords()),
                        OperationLog::getOptDesc, condition.getKeywords())
                .orderByDesc(OperationLog::getId));
        List<OperationLogDTO> operationLogDTOList =
                BeanCopyUtils.copyList(operationLogPage.getRecords(), OperationLogDTO.class);
        return new PageResult<>(operationLogDTOList, (int) operationLogPage.getTotal());
    }

}
