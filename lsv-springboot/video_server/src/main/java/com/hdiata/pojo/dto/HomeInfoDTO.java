package com.hdiata.pojo.dto;

import com.hdiata.pojo.vo.WebsiteConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 首页信息
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 17:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeInfoDTO {

    /**
     * 轮播图列表
     */
    private List<String> photoList;

    /**
     * 网站配置
     */
    private WebsiteConfigVO websiteConfig;

    /**
     * 视频排行榜
     */
    private List<VideoRankDTO> videoRankDTOList;

}
