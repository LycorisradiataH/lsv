package com.hdiata.common.constant;

/**
 * redis 常量
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/26 15:49
 */
public class RedisPrefixConst {

    /**
     * 验证码过期时间
     */
    public static final long CODE_EXPIRE_TIME = 15 * 60;

    /**
     * 验证码
     */
    public static final String USER_CODE_KEY = "code:";

    /**
     * 总浏览量
     */
    public static final String TOTAL_VIEW_COUNT = "total_view_count";

    /**
     * 视频点击量
     */
    public static final String VIDEO_VIEW_COUNT = "video_view_count";

    /**
     * 视频点赞量
     */
    public static final String VIDEO_LIKE_COUNT = "video_like_count";

    /**
     * 用户点赞视频
     */
    public static final String VIDEO_USER_LIKE = "video_user_like:";

    /**
     * 评论点赞量
     */
    public static final String COMMENT_LIKE_COUNT = "comment_like_count";

    /**
     * 用户点赞评论
     */
    public static final String COMMENT_USER_LIKE = "comment_user_like:";

    /**
     * 网站配置
     */
    public static final String WEBSITE_CONFIG = "website_config";

    /**
     * 用户地区
     */
    public static final String USER_AREA = "user_area";

    /**
     * 访客地区
     */
    public static final String VISITOR_AREA = "visitor_area";

    /**
     * 关于作者信息
     */
    public static final String ABOUT = "about";

    /**
     * 访客
     */
    public static final String UNIQUE_VISITOR = "unique_visitor";

}
