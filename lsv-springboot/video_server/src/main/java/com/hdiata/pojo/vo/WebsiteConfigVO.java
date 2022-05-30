package com.hdiata.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 网站配置
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/2 18:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "网站配置")
public class WebsiteConfigVO {

    /**
     * 网站头像
     */
    @ApiModelProperty(name = "websiteAvatar", value = "网站头像", required = true, dataType = "String")
    private String websiteAvatar;

    /**
     * 网站名称
     */
    @ApiModelProperty(name = "websiteName", value = "网站名称", required = true, dataType = "String")
    private String websiteName;

    /**
     * 网站作者
     */
    @ApiModelProperty(name = "websiteAuthor", value = "网站作者", required = true, dataType = "String")
    private String websiteAuthor;

    /**
     * 网站介绍
     */
    @ApiModelProperty(name = "websiteIntro", value = "网站介绍", required = true, dataType = "String")
    private String websiteIntro;

    /**
     * 网站公告
     */
    @ApiModelProperty(name = "websiteNotice", value = "网站公告", required = true, dataType = "String")
    private String websiteNotice;

    /**
     * 网站创建时间
     */
    @ApiModelProperty(name = "websiteCreateTime", value = "网站创建时间", required = true, dataType = "LocalDateTime")
    private String websiteCreateTime;

    /**
     * ICP备案号
     */
    @ApiModelProperty(name = "websiteRecordNo", value = "ICP备案号", required = true, dataType = "String")
    private String websiteRecordNo;

    /**
     * 公网安备案号
     */
    @ApiModelProperty(name = "websiteBeianNo", value = "公网安备案号", required = true, dataType = "String")
    private String websiteBeianNo;

    /**
     * 社交登录列表
     */
    @ApiModelProperty(name = "socialLoginList", value = "社交登录列表", required = true, dataType = "List<String>")
    private List<String> socialLoginList;

    /**
     * 社交url列表
     */
    @ApiModelProperty(name = "socialUrlList", value = "社交url列表", required = true, dataType = "List<String>")
    private List<String> socialUrlList;

    /**
     * qq
     */
    @ApiModelProperty(name = "qq", value = "qq", required = true, dataType = "String")
    private String qq;

    /**
     * github
     */
    @ApiModelProperty(name = "github", value = "github", required = true, dataType = "String")
    private String github;

    /**
     * gitee
     */
    @ApiModelProperty(name = "gitee", value = "gitee", required = true, dataType = "String")
    private String gitee;

    /**
     * 游客头像
     */
    @ApiModelProperty(name = "touristAvatar", value = "游客头像", required = true, dataType = "String")
    private String touristAvatar;

    /**
     * 用户头像
     */
    @ApiModelProperty(name = "userAvatar", value = "用户头像", required = true, dataType = "String")
    private String userAvatar;

    /**
     * 是否视频审核
     */
    @ApiModelProperty(name = "isVideoReview", value = "是否视频审核", required = true, dataType = "Integer")
    private Integer isVideoReview;

    /**
     * 是否评论审核
     */
    @ApiModelProperty(name = "isCommentReview", value = "是否评论审核", required = true, dataType = "Integer")
    private Integer isCommentReview;

    /**
     * 是否邮箱通知
     */
    @ApiModelProperty(name = "isEmailNotice", value = "是否邮箱通知", required = true, dataType = "Integer")
    private Integer isEmailNotice;

    /**
     * 是否打赏
     */
    @ApiModelProperty(name = "isReward", value = "是否打赏", required = true, dataType = "Integer")
    private Integer isReward;

    /**
     * 微信二维码
     */
    @ApiModelProperty(name = "weiXinQRCode", value = "微信二维码", required = true, dataType = "String")
    private String weiXinQRCode;

    /**
     * 支付宝二维码
     */
    @ApiModelProperty(name = "alipayQRCode", value = "支付宝二维码", required = true, dataType = "String")
    private String alipayQRCode;

    /**
     * websocket地址
     */
    @ApiModelProperty(name = "websocketUrl", value = "websocket地址", required = true, dataType = "String")
    private String websocketUrl;

}