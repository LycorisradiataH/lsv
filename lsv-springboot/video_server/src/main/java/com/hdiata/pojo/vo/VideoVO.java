package com.hdiata.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 视频
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/30 17:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "视频")
public class VideoVO {

    /**
     * 视频id
     */
    @ApiModelProperty(name = "id", value = "视频id", dataType = "Integer")
    private Integer id;

    /**
     * 视频名
     */
    @NotBlank(message = "视频名不能为空")
    @ApiModelProperty(name = "videoName", value = "文章标题", required = true, dataType = "String")
    private String videoName;

    /**
     * 视频封面
     */
    @ApiModelProperty(name = "videoCover", value = "视频缩略图", dataType = "String")
    private String videoCover;

    /**
     * 视频分类
     */
    @ApiModelProperty(name = "category", value = "视频分类", dataType = "Integer")
    private String categoryName;

    /**
     * 视频标签
     */
    @ApiModelProperty(name = "tagNameList", value = "视频标签", dataType = "List<Integer>")
    private List<String> tagNameList;

    /**
     * 视频url
     */
    @ApiModelProperty(name = "videoUrl", value = "视频url", dataType = "String")
    private String videoUrl;

    /**
     * 播放id
     */
    @ApiModelProperty(name = "playId", value = "播放id", dataType = "String")
    private String playId;

    /**
     * 是否置顶
     */
    @ApiModelProperty(name = "isTop", value = "是否置顶", dataType = "Integer")
    private Integer isTop;

}
