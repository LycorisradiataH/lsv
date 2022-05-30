package com.hdiata.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 时区枚举
 * @author Lin Hua
 * @version 1.0
 * @date 12/3/2022 上午11:21
 */
@Getter
@AllArgsConstructor
public enum ZoneEnum {

    /**
     * 上海
     */
    SHANGHAI("Asia/Shanghai", "中国上海");

    /**
     * 时区
     */
    private final String zone;

    /**
     * 描述
     */
    private final String desc;

}
