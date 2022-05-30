package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 首页视频
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/21 16:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoHomeDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 视频缩略图
     */
    private String videoCover;

    /**
     * 视频名
     */
    private String videoName;

    /**
     * 视频url
     */
    private String videoUrl;

    /**
     * 播放id
     */
    private String playId;

    /**
     * 视频长度
     */
    private Integer duration;

    /**
     * 发表时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 视频分类id
     */
    private Long categoryId;

    /**
     * 视频分类名
     */
    private String categoryName;

    /**
     * 视频标签
     */
    private List<TagDTO> tagDTOList;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 浏览量
     */
    private Integer viewCount;

}
