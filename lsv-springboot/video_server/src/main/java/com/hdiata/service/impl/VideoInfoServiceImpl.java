package com.hdiata.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hdiata.mapper.*;
import com.hdiata.pojo.dto.*;
import com.hdiata.pojo.entity.Photo;
import com.hdiata.pojo.entity.PhotoAlbum;
import com.hdiata.pojo.entity.Video;
import com.hdiata.pojo.entity.WebsiteConfig;
import com.hdiata.pojo.vo.VideoInfoVO;
import com.hdiata.pojo.vo.WebsiteConfigVO;
import com.hdiata.service.UniqueViewService;
import com.hdiata.service.VideoInfoService;
import com.hdiata.util.BeanCopyUtils;
import com.hdiata.util.IpUtils;
import com.hdiata.util.RedisUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.hdiata.common.constant.CommonConst.*;
import static com.hdiata.common.constant.RedisPrefixConst.*;

/**
 * 视频信息服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 17:06
 */
@Service
public class VideoInfoServiceImpl implements VideoInfoService {

    @Autowired
    private PhotoAlbumMapper photoAlbumMapper;

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UniqueViewService uniqueViewService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private WebsiteConfigMapper websiteConfigMapper;

    @Resource
    private HttpServletRequest request;

    @Override
    public HomeInfoDTO getHomeInfo() {
        // 获取首页轮播图图片
        PhotoAlbum photoAlbum = photoAlbumMapper.selectOne(new LambdaQueryWrapper<PhotoAlbum>()
                .select(PhotoAlbum::getId)
                .eq(PhotoAlbum::getStatus, TRUE));
        List<String> photoList = new ArrayList<>();
        if (Objects.nonNull(photoAlbum)) {
            photoList = photoMapper.selectList(new LambdaQueryWrapper<Photo>()
                    .eq(Photo::getAlbumId, photoAlbum.getId()))
                    .stream()
                    .map(Photo::getPhotoSrc)
                    .collect(Collectors.toList());
        }
        // 查询网站配置
        WebsiteConfigVO websiteConfig = this.getWebsiteConfig();
        HomeInfoDTO homeInfo = HomeInfoDTO.builder()
                .photoList(photoList)
                .websiteConfig(websiteConfig)
                .build();
        // 获取视频点击量排行前七的数据
        Map<Object, Double> videoMap =
                redisUtils.zReverseRangeWithScore(VIDEO_VIEW_COUNT, 0, 6);
        if (CollectionUtils.isNotEmpty(videoMap)) {
            // 查询视频排行
            homeInfo.setVideoRankDTOList(listVideoRank(videoMap));
        }
        return homeInfo;
    }

    @Override
    public HomeBackInfoDTO getHomeBackInfo() {
        // 查询访问量
        Object count = redisUtils.get(TOTAL_VIEW_COUNT);
        Integer viewCount = Integer.valueOf(Optional.ofNullable(count).orElse(0).toString());
        // 查询评论量
        Integer commentCount = commentMapper.selectCount(null);
        // 查询用户量
        Integer userCount = userInfoMapper.selectCount(null);
        // 查询视频量
        Integer videoCount = videoMapper.selectCount(new LambdaQueryWrapper<Video>()
                .eq(Video::getIsDelete, FALSE));
        // 查询一周用户量
        List<UniqueViewDTO> uniqueViewDTOList = uniqueViewService.listUniqueView();
        // 查询分类数据
        List<CategoryDTO> categoryDTOList = categoryMapper.listCategoryDTO();
        // 查询标签数据
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagMapper.selectList(null), TagDTO.class);
        // 查询redis访问量前五的视频
        Map<Object, Double> videoMap =
                redisUtils.zReverseRangeWithScore(VIDEO_VIEW_COUNT, 0, 4);
        HomeBackInfoDTO homeBackInfo = HomeBackInfoDTO.builder()
                .tagDTOList(tagDTOList)
                .viewCount(viewCount)
                .commentCount(commentCount)
                .userCount(userCount)
                .videoCount(videoCount)
                .categoryDTOList(categoryDTOList)
                .uniqueViewDTOList(uniqueViewDTOList)
                .build();
        if (CollectionUtils.isNotEmpty(videoMap)) {
            // 查询视频排行
            homeBackInfo.setVideoBackRankDTOList(listVideoBackRank(videoMap));
        }
        return homeBackInfo;
    }

    @Override
    public WebsiteConfigVO getWebsiteConfig() {
        WebsiteConfigVO websiteConfigVO;
        // 获取缓存数据
        Object websiteConfig = redisUtils.get(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigVO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVO.class);
        } else {
            // 从数据库中加载
            String config = websiteConfigMapper.selectById(1).getConfig();
            websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class);
            redisUtils.set(WEBSITE_CONFIG, config);
        }
        return websiteConfigVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        // 修改网站配置
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .id(1)
                .config(JSON.toJSONString(websiteConfigVO))
                .build();
        websiteConfigMapper.updateById(websiteConfig);
        // 删除缓存
        redisUtils.del(WEBSITE_CONFIG);
    }

    @Override
    public String getAbout() {
        Object aboutAuthor = redisUtils.get(ABOUT);
        return Objects.nonNull(aboutAuthor) ? aboutAuthor.toString() : "";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAbout(VideoInfoVO videoInfo) {
        redisUtils.set(ABOUT, videoInfo.getAboutContent());
    }

    @Override
    public void report() {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!redisUtils.sHasKey(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtils.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = ipSource.substring(0, 2)
                        .replaceAll(PROVINCE, "")
                        .replaceAll(CITY, "");
                redisUtils.hIncr(VISITOR_AREA, ipSource, 1L);
            } else {
                redisUtils.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            // 访问量+1
            redisUtils.incr(TOTAL_VIEW_COUNT, 1);
            // 保存唯一标识
            redisUtils.sSet(UNIQUE_VISITOR, md5);
        }
    }

    /**
     * 查询视频排行
     * @param videoMap 视频信息
     * @return {@link List<VideoRankDTO>} 视频排行
     */
    private List<VideoRankDTO> listVideoRank(Map<Object, Double> videoMap) {
        // 提取视频id
        List<Integer> videoIdList = new ArrayList<>();
        videoMap.forEach((key, value) -> videoIdList.add(Integer.parseInt(key.toString())));
        // 查询视频信息
        return videoMapper.selectList(new LambdaQueryWrapper<Video>()
                .select(Video::getId, Video::getVideoName, Video::getVideoCover)
                .in(Video::getId, videoIdList))
                .stream().map(video -> VideoRankDTO.builder()
                        .id(video.getId())
                        .videoName(video.getVideoName())
                        .videoCover(video.getVideoCover())
                        .viewCount(videoMap.get(video.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingLong(VideoRankDTO::getViewCount).reversed())
                .collect(Collectors.toList());
    }

    /**
     * 查询后台视频排行
     * @param videoMap 视频信息
     * @return {@link List<VideoBackRankDTO>} 后台视频排行
     */
    private List<VideoBackRankDTO> listVideoBackRank(Map<Object, Double> videoMap) {
        // 提取视频id
        List<Integer> videoIdList = new ArrayList<>();
        videoMap.forEach((key, value) -> videoIdList.add(Integer.parseInt(key.toString())));
        // 查询视频信息
        return videoMapper.selectList(new LambdaQueryWrapper<Video>()
                .select(Video::getId, Video::getVideoName)
                .in(Video::getId, videoIdList))
                .stream().map(video -> VideoBackRankDTO.builder()
                        .videoName(video.getVideoName())
                        .viewCount(videoMap.get(video.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingLong(VideoBackRankDTO::getViewCount).reversed())
                .collect(Collectors.toList());
    }

}
