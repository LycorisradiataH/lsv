package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户地区
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/28 14:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAreaDTO {

    /**
     * 地区名
     */
    private String name;

    /**
     * 数量
     */
    private Long value;

}
