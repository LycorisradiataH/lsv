package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台分类
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 15:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBackDTO {

    /**
     * 分类id
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 视频数量
     */
    private Integer videoCount;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

}
