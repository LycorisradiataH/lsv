package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 搜索视频
 * @author Lin Hua
 * @version 1.0
 * @date 2022/4/25 10:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoSearchDTO {

    /**
     * 视频id
     */
    private Integer id;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 视频封面图
     */
    private String videoCover;

    /**
     * 播放id
     */
    private String playId;

    /**
     * 播放时长
     */
    private Integer duration;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 是否删除
     */
    private Integer isDelete;

}
