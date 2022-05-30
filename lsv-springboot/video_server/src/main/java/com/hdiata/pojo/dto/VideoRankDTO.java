package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频排行
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/26 15:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRankDTO {

    /**
     * 视频id
     */
    private Integer id;

    /**
     * 视频名
     */
    private String videoName;

    /**
     * 视频封面图
     */
    private String videoCover;

    /**
     * 浏览量
     */
    private Integer viewCount;

}
