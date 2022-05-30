package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类选项
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 16:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOptionDTO {

    /**
     * 分类id
     */
    private Integer id;

    /**
     * 分类名
     */
    private String categoryName;

}
