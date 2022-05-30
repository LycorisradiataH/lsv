package com.hdiata.controller;

import com.hdiata.common.annotation.OptLog;
import com.hdiata.common.enums.FilePathEnum;
import com.hdiata.common.strategy.context.UploadStrategyContext;
import com.hdiata.pojo.dto.*;
import com.hdiata.pojo.vo.*;
import com.hdiata.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.hdiata.common.constant.OptTypeConst.*;

/**
 * 视频控制器
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/21 16:42
 */
@Api(tags = "视频模块")
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    /**
     * 查看首页视频
     * @return {@link Result<VideoHomeDTO>} 首页视频列表
     */
    @ApiOperation(value = "查看首页视频")
    @GetMapping("/videos")
    public Result<List<VideoHomeDTO>> listVideo() {
        return Result.success(videoService.listVideo());
    }

    /**
     * 搜索视频
     * @param condition 条件
     * @return {@link Result<VideoSearchDTO>} 视频列表
     */
    @ApiOperation(value = "搜索视频")
    @GetMapping("/videos/search")
    public Result<List<VideoSearchDTO>> listVideosBySearch(ConditionVO condition) {
        return Result.success(videoService.listVideosBySearch(condition));
    }

    /**
     * 根据条件查询视频
     * @param condition 条件
     * @return {@link Result<VideoPreviewListDTO>} 视频列表
     */
    @ApiOperation(value = "根据条件查询视频")
    @GetMapping("/videos/condition")
    public Result<VideoPreviewListDTO> listVideosByCondition(ConditionVO condition) {
        return Result.success(videoService.listVideosByCondition(condition));
    }

    /**
     * 根据用户id查询视频
     * @param condition 条件
     * @return {@link Result<VideoHomeDTO>} 视频列表
     */
    @ApiOperation(value = "根据条件查询视频")
    @GetMapping("/videos/condition/userId")
    public Result<List<VideoHomeDTO>> listVideosByUserId(ConditionVO condition) {
        return Result.success(videoService.listVideosByUserId(condition));
    }

    /**
     * 上传视频缩略图
     * @param file 文件
     * @return {@link Result<String>} 视频缩略图地址
     */
    @ApiOperation(value = "上传视频缩略图")
    @ApiImplicitParam(name = "file", value = "视频缩略图", required = true, dataType = "MultipartFile")
    @PostMapping("/videos/images")
    public Result<String> saveVideoImages(MultipartFile file) {
        return Result.success(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.VIDEO.getPath()));
    }

    /**
     * 上传视频
     * @param file 文件
     * @return {@link Result<String>} 视频播放id
     */
    @ApiOperation(value = "上传视频")
    @ApiImplicitParam(name = "file", value = "视频", required = true, dataType = "MultipartFile")
    @PostMapping("/videos/video")
    public Result<String> saveVideo(MultipartFile file) {
        return Result.success(videoService.saveVideo(file));
    }

    /**
     * 添加或修改视频
     * @param videoVO 视频信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改视频")
    @PostMapping("/videos")
    public Result<?> saveOrUpdateVideo(@Valid @RequestBody VideoVO videoVO) {
        videoService.saveOrUpdateVideo(videoVO);
        return Result.success();
    }

    /**
     * 根据id查看视频
     * @param videoId 视频id
     * @return {@link Result<VideoDTO>} 视频信息
     */
    @ApiOperation(value = "根据id查看视频")
    @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType = "Integer")
    @GetMapping("/videos/{videoId}")
    public Result<VideoDTO> getVideoById(@PathVariable("videoId") Integer videoId) {
        return Result.success(videoService.getVideoById(videoId));
    }

    /**
     * 点赞视频
     * @param videoId 视频id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "点赞视频")
    @ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType = "Integer")
    @PostMapping("/videos/{videoId}/like")
    public Result<?> saveVideoLike(@PathVariable("videoId") Integer videoId) {
        videoService.saveVideoLike(videoId);
        return Result.success();
    }

    /**
     * 查看后台视频
     * @param condition 查询条件
     * @return {@link Result<VideoBackDTO>} 后台视频列表
     */
    @ApiOperation(value = "查看后台视频")
    @GetMapping("/admin/videos")
    public Result<PageResult<VideoBackDTO>> listVideoBack(ConditionVO condition) {
        return Result.success(videoService.listVideoBack(condition));
    }

    /**
     * 恢复或删除视频
     * @param delete 逻辑删除视频
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "恢复或删除视频")
    @PutMapping("/admin/videos")
    public Result<?> updateVideoDelete(@Valid @RequestBody DeleteVO delete) {
        videoService.updateVideoDelete(delete);
        return Result.success();
    }

    /**
     * 删除视频
     * @param videoIdList 视频id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "物理删除视频")
    @DeleteMapping("/admin/videos")
    public Result<?> deleteVideos(@RequestBody List<Integer> videoIdList) {
        videoService.deleteVideos(videoIdList);
        return Result.success();
    }

}
