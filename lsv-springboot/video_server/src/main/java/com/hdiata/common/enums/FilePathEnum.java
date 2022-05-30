package com.hdiata.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件路径枚举
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/18 16:47
 */
@Getter
@AllArgsConstructor
public enum FilePathEnum {

    /**
     * 头像路径
     */
    AVATAR("avatar/", "头像路径"),

    /**
     * 视频封面图路径
     */
    VIDEO("videos/", "视频图片路径"),

    /**
     * 照片路径
     */
    PHOTO("photos/","相册路径"),

    /**
     * 配置图片路径
     */
    CONFIG("config/","配置图片路径");

    /**
     * 路径
     */
    private final String path;

    /**
     * 描述
     */
    private final String desc;

}
