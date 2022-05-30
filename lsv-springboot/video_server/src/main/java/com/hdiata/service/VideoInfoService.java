package com.hdiata.service;

import com.hdiata.pojo.dto.HomeBackInfoDTO;
import com.hdiata.pojo.dto.HomeInfoDTO;
import com.hdiata.pojo.vo.VideoInfoVO;
import com.hdiata.pojo.vo.WebsiteConfigVO;

/**
 * 视频信息服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 17:05
 */
public interface VideoInfoService {

    /**
     * 获取首页数据
     * @return 首页信息
     */
    HomeInfoDTO getHomeInfo();

    /**
     * 获取后台数据
     * @return 后台信息
     */
    HomeBackInfoDTO getHomeBackInfo();

    /**
     * 获取网站配置
     * @return {@link WebsiteConfigVO} 网站配置
     */
    WebsiteConfigVO getWebsiteConfig();

    /**
     * 保存或更新网站配置
     * @param websiteConfigVO 网站配置
     */
    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    /**
     * 获取关于作者内容
     * @return 关于作者内容
     */
    String getAbout();

    /**
     * 修改关于作者内容
     * @param videoInfo 视频信息
     */
    void updateAbout(VideoInfoVO videoInfo);

    /**
     * 上传访客信息
     */
    void report();

}
