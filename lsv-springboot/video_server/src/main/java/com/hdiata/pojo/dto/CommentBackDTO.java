package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台评论
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 17:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentBackDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 被回复用户昵称
     */
    private String replyNickname;

    /**
     * 视频名
     */
    private String videoName;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论类型
     */
    private Integer type;

    /**
     * 是否审核
     */
    private Integer isReview;

    /**
     * 发表时间
     */
    private LocalDateTime gmtCreate;

}
