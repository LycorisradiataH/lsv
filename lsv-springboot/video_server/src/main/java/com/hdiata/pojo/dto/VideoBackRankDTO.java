package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台视频排行
 * @author Lin Hua
 * @version 1.0
 * @date 2022/4/24 9:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoBackRankDTO {

    /**
     * 视频名
     */
    private String videoName;

    /**
     * 浏览量
     */
    private Integer viewCount;

}
