package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访问量
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/26 15:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniqueViewDTO {

    /**
     * 日期
     */
    private String day;

    /**
     * 访问量
     */
    private Integer viewCount;

}
