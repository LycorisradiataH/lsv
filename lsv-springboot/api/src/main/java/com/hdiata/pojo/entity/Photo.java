package com.hdiata.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 照片实体类
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/18 16:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

    /**
     * 照片id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 相册id
     */
    private Integer albumId;

    /**
     * 照片名
     */
    private String photoName;

    /**
     * 照片描述
     */
    private String photoDesc;

    /**
     * 照片地址
     */
    private String photoSrc;

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
