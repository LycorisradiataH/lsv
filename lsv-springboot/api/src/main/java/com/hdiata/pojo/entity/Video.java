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
 * 视频实体类
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/19 16:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 作者id
     */
    private Integer userId;

    /**
     * 视频分类
     */
    private Integer categoryId;

    /**
     * 视频缩略图
     */
    private String videoCover;

    /**
     * 视频名
     */
    private String videoName;

    /**
     * 视频链接
     */
    private String videoUrl;

    /**
     * 播放id
     */
    private String playId;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 是否删除
     */
    private Integer isDelete;

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
