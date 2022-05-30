package com.hdiata.controller;

import com.hdiata.pojo.dto.OperationLogDTO;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.Result;
import com.hdiata.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志控制器
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/2 18:43
 */
@Api(tags = "日志模块")
@RestController
public class LogController {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 查看操作日志
     * @param condition 查询条件
     * @return {@link Result<OperationLogDTO>} 日志列表
     */
    @ApiOperation(value = "查看操作日志")
    @GetMapping("/admin/operation/logs")
    public Result<PageResult<OperationLogDTO>> listOperationLogs(ConditionVO condition) {
        return Result.success(operationLogService.listOperationLogs(condition));
    }

    /**
     * 删除操作日志
     * @param logIdList 日志id列表
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除操作日志")
    @DeleteMapping("/admin/operation/logs")
    public Result<?> deleteOperationLogs(@RequestBody List<Integer> logIdList) {
        operationLogService.removeByIds(logIdList);
        return Result.success();
    }

}
