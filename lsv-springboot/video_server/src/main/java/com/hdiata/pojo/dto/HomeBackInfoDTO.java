package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 视频后台信息
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/26 15:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeBackInfoDTO {

    /**
     * 访问量
     */
    private Integer viewCount;

    /**
     * 评论量
     */
    private Integer commentCount;

    /**
     * 用户量
     */
    private Integer userCount;

    /**
     * 视频量
     */
    private Integer videoCount;

    /**
     * 分类统计
     */
    private List<CategoryDTO> categoryDTOList;

    /**
     * 标签列表
     */
    private List<TagDTO> tagDTOList;

    /**
     * 一周用户量集合
     */
    private List<UniqueViewDTO> uniqueViewDTOList;

    /**
     * 视频浏览量排行
     */
    private List<VideoBackRankDTO> videoBackRankDTOList;

}
