package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.*;
import com.hdiata.pojo.entity.Video;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.DeleteVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.VideoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 视频服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:51
 */
public interface VideoService extends IService<Video> {

    /**
     * 查询首页视频
     * @return 视频列表
     */
    List<VideoHomeDTO> listVideo();

    /**
     * 搜索视频
     * @param condition 条件
     * @return 视频列表
     */
    List<VideoSearchDTO> listVideosBySearch(ConditionVO condition);

    /**
     * 根据条件查询视频列表
     * @param condition 条件
     * @return 视频列表
     */
    VideoPreviewListDTO listVideosByCondition(ConditionVO condition);

    /**
     * 根据用户id查询视频列表
     * @param condition 条件
     * @return 视频列表
     */
    List<VideoHomeDTO> listVideosByUserId(ConditionVO condition);

    /**
     * 上传视频
     * @param file 文件
     * @return 视频id
     */
    String saveVideo(MultipartFile file);

    /**
     * 添加或修改视频
     * @param videoVO 视频信息
     */
    void saveOrUpdateVideo(VideoVO videoVO);

    /**
     * 根据id查看视频
     * @param videoId 视频id
     * @return {@link VideoDTO} 视频信息
     */
    VideoDTO getVideoById(Integer videoId);

    /**
     * 点赞视频
     * @param videoId 视频id
     */
    void saveVideoLike(Integer videoId);

    /**
     * 查询后台视频
     * @param condition 查询条件
     * @return 视频列表
     */
    PageResult<VideoBackDTO> listVideoBack(ConditionVO condition);

    /**
     * 删除或恢复视频
     * @param delete 逻辑删除对象
     */
    void updateVideoDelete(DeleteVO delete);

    /**
     * 物理删除视频
     * @param videoIdList 视频id集合
     */
    void deleteVideos(List<Integer> videoIdList);

}
