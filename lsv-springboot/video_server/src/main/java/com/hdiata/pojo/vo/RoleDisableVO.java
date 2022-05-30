package com.hdiata.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 角色禁用状态
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/16 18:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "角色禁用状态")
public class RoleDisableVO {

    /**
     * id
     */
    @NotNull(message = "角色id不能为空")
    private Integer id;

    /**
     * 禁用状态
     */
    @NotNull(message = "禁用状态不能为空")
    private Integer isDisable;

}
