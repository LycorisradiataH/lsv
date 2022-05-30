package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 视频
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/31 15:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 作者id
     */
    private Integer authorId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 视频缩略图
     */
    private String videoCover;

    /**
     * 视频名
     */
    private String videoName;

    /**
     * 播放id
     */
    private String playId;

    /**
     * 播放密钥
     */
    private String playAuth;

    /**
     * 播放地址
     */
    private String url;

    /**
     * 点赞量
     */
    private Integer likeCount;

    /**
     * 浏览量
     */
    private Integer viewsCount;

    /**
     * 发表时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

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

    ///**
    // * 上一篇视频
    // */
    //private VideoPaginationDTO lastVideo;
    //
    ///**
    // * 下一篇视频
    // */
    //private VideoPaginationDTO nextVideo;

    /**
     * 推荐视频列表
     */
    private List<VideoRecommendDTO> recommendVideoList;

    /**
     * 最新视频列表
     */
    private List<VideoRecommendDTO> newestVideoList;

}
