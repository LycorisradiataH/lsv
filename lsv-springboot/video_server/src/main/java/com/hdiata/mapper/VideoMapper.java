package com.hdiata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdiata.pojo.dto.*;
import com.hdiata.pojo.entity.Video;
import com.hdiata.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 视频
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:48
 */
@Repository
public interface VideoMapper extends BaseMapper<Video> {

    /**
     * 查看首页视频
     * @param current 页码
     * @param size 大小
     * @return 视频列表
     */
    List<VideoHomeDTO> listVideo(@Param("current") Long current, @Param("size") Long size);

    /**
     * 根据条件查询视频
     * @param current 页码
     * @param size 大小
     * @param condition 条件
     * @return 视频列表
     */
    List<VideoPreviewDTO> listVideosByCondition(@Param("current") Long current,
                                                @Param("size") Long size,
                                                @Param("condition") ConditionVO condition);

    /**
     * 查询后台视频总量
     * @param condition 查询条件
     * @return 视频总量
     */
    Integer countVideoBack(@Param("condition") ConditionVO condition);

    /**
     * 查询后台视频
     * @param current 页码
     * @param size 大小
     * @param condition 查询条件
     * @return 视频列表
     */
    List<VideoBackDTO> listVideoBack(@Param("current") Long current,
                                     @Param("size") Long size,
                                     @Param("condition") ConditionVO condition);

    /**
     * 查看视频的推荐视频
     * @param videoId 视频id
     * @return 视频列表
     */
    List<VideoRecommendDTO> listRecommendVideos(@Param("videoId") Integer videoId);

    /**
     * 根据id查询视频
     * @param videoId 视频id
     * @return 视频信息
     */
    VideoDTO getArticleById(Integer videoId);
}
