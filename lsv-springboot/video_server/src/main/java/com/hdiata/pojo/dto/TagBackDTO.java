package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 后台标签
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 17:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagBackDTO {

    /**
     * 标签id
     */
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 视频量
     */
    private Integer videoCount;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

}
