package com.hdiata.controller;

import com.hdiata.common.annotation.OptLog;
import com.hdiata.pojo.dto.HomeBackInfoDTO;
import com.hdiata.pojo.dto.HomeInfoDTO;
import com.hdiata.pojo.vo.Result;
import com.hdiata.pojo.vo.VideoInfoVO;
import com.hdiata.pojo.vo.WebsiteConfigVO;
import com.hdiata.service.VideoInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.hdiata.common.constant.OptTypeConst.UPDATE;

/**
 * 视频信息控制器
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 17:05
 */
@Api(tags = "视频信息模块")
@RestController
public class VideoInfoController {

    @Autowired
    public VideoInfoService videoInfoService;

    /**
     * 查看主页信息
     * @return {@link Result<HomeInfoDTO>} 主页信息
     */
    @ApiOperation(value = "查看主页信息")
    @GetMapping("/")
    public Result<HomeInfoDTO> getHomeInfo() {
        return Result.success(videoInfoService.getHomeInfo());
    }

    /**
     * 查看后台信息
     * @return {@link Result<HomeBackInfoDTO>} 后台信息
     */
    @ApiOperation(value = "查看后台信息")
    @GetMapping("/admin")
    public Result<HomeBackInfoDTO> getHomeBackInfo() {
        return Result.success(videoInfoService.getHomeBackInfo());
    }

    /**
     * 获取网站配置
     * @return {@link Result<WebsiteConfigVO>} 网站配置
     */
    @ApiOperation(value = "获取网站配置")
    @GetMapping("/admin/website/config")
    public Result<WebsiteConfigVO> getWebsiteConfig() {
        return Result.success(videoInfoService.getWebsiteConfig());
    }

    /**
     * 更新网站配置
     * @param websiteConfigVO 网站配置信息
     * @return {@link Result}
     */
    @ApiOperation(value = "更新网站配置")
    @PutMapping("/admin/website/config")
    public Result<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        videoInfoService.updateWebsiteConfig(websiteConfigVO);
        return Result.success();
    }

    /**
     * 查看关于作者信息
     * @return {@link Result<String>} 关于作者信息
     */
    @ApiOperation(value = "查看关于作者信息")
    @GetMapping("/about")
    public Result<String> getAbout() {
        return Result.success(videoInfoService.getAbout());
    }

    /**
     * 修改关于作者信息
     * @param videoInfo 视频信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改关于作者信息")
    @PutMapping("/admin/about")
    public Result<?> updateAbout(@Valid @RequestBody VideoInfoVO videoInfo) {
        videoInfoService.updateAbout(videoInfo);
        return Result.success();
    }

    /**
     * 上传访客信息
     * @return {@link Result}
     */
    @PostMapping("/report")
    public Result<?> report() {
        videoInfoService.report();
        return Result.success();
    }

}
