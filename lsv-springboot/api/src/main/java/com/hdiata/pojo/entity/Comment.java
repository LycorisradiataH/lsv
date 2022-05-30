package com.hdiata.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 评论实体类
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/19 15:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 评论用户Id
     */
    private Integer userId;

    /**
     * 评论视频id
     */
    private Integer videoId;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 回复用户id
     */
    private Integer replyUserId;

    /**
     * 父评论id
     */
    private Integer parentId;

    /**
     * 评论类型 1.视频 2.贴子
     */
    private Integer type;

    /**
     * 是否审核
     */
    private Integer isReview;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

}
