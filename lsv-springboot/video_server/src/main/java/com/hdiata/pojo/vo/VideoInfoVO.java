package com.hdiata.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频信息
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/2 18:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "视频信息")
public class VideoInfoVO {

    /**
     * 关于作者内容
     */
    @ApiModelProperty(name = "aboutContent", value = "关于作者内容", required = true, dataType = "String")
    private String aboutContent;

}
