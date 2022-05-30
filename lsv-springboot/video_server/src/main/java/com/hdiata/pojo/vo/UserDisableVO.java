package com.hdiata.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 用户禁用状态
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/2 17:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户禁用状态")
public class UserDisableVO {

    /**
     * id
     */
    @NotNull(message = "用户id不能为空")
    private Integer id;

    /**
     * 禁用状态
     */
    @NotNull(message = "禁用状态不能为空")
    private Integer isDisable;

}

