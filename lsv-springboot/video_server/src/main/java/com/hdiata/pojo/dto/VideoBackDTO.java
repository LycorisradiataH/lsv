package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台视频
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/26 17:44
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoBackDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 视频封面
     */
    private String videoCover;

    /**
     * 视频名
     */
    private String videoName;

    /**
     * 发表时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 视频分类名
     */
    private String categoryName;

    /**
     * 视频标签
     */
    private List<TagDTO> tagDTOList;

    /**
     * 作者
     */
    private String author;

    /**
     * 是否删除
     */
    private Integer isDelete;

}
