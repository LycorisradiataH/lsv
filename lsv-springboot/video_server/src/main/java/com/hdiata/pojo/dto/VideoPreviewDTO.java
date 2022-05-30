package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 预览视频
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/30 16:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoPreviewDTO {

    /**
     * 视频id
     */
    private Integer id;

    /**
     * 视频缩略图
     */
    private String videoCover;

    /**
     * 标题
     */
    private String videoName;

    /**
     * 播放id
     */
    private String playId;

    /**
     * 发表时间
     */
    private Date gmtCreate;

    /**
     * 视频分类id
     */
    private Integer categoryId;

    /**
     * 视频分类名
     */
    private String categoryName;

    /**
     * 视频标签
     */
    private List<TagDTO> tagDTOList;

}
