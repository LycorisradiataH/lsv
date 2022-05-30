package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 推荐视频
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/31 15:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRecommendDTO {

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
     * 创建时间
     */
    private LocalDateTime gmtCreate;

}
