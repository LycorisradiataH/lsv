package com.hdiata.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 菜单隐藏状态
 * @author Lin Hua
 * @version 1.0
 * @date 2022/5/5 15:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "菜单隐藏状态")
public class MenuHiddenVO {

    /**
     * id
     */
    @NotNull(message = "菜单id不能为空")
    private Integer id;

    /**
     * 隐藏状态
     */
    @NotNull(message = "隐藏状态不能为空")
    private Integer isHidden;

}
