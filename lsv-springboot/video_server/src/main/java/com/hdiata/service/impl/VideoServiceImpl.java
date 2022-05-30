package com.hdiata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.common.exception.ServiceException;
import com.hdiata.mapper.CategoryMapper;
import com.hdiata.mapper.VideoMapper;
import com.hdiata.mapper.VideoTagMapper;
import com.hdiata.pojo.dto.*;
import com.hdiata.pojo.entity.Category;
import com.hdiata.pojo.entity.Tag;
import com.hdiata.pojo.entity.Video;
import com.hdiata.pojo.entity.VideoTag;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.DeleteVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.VideoVO;
import com.hdiata.service.TagService;
import com.hdiata.service.VideoService;
import com.hdiata.service.VideoTagService;
import com.hdiata.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.hdiata.common.constant.CommonConst.*;
import static com.hdiata.common.constant.RedisPrefixConst.*;

/**
 * 视频服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:51
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private VideoTagService videoTagService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private VideoTagMapper videoTagMapper;

    @Autowired
    private VodUtils vodUtils;

    @Autowired
    private HttpSession session;

    @Override
    public List<VideoHomeDTO> listVideo() {
        List<VideoHomeDTO> videoHomeDTOList =
                videoMapper.listVideo(PageUtils.getLimitCurrent(), PageUtils.getSize());
        videoHomeDTOList.forEach(item ->
                item.setDuration(vodUtils.getVideoDuration(item.getPlayId())));
        for (VideoHomeDTO videoHomeDTO : videoHomeDTOList) {
            // 封装点赞量和浏览量
            Double score = redisUtils.zScore(VIDEO_VIEW_COUNT, videoHomeDTO.getId());
            if (Objects.nonNull(score)) {
                videoHomeDTO.setViewCount(score.intValue());
            }
            videoHomeDTO.setLikeCount((Integer) redisUtils.hGet(VIDEO_LIKE_COUNT,
                    videoHomeDTO.getId().toString()));
        }
        return videoHomeDTOList;
    }

    @Override
    public List<VideoSearchDTO> listVideosBySearch(ConditionVO condition) {
        String keywords = condition.getKeywords();
        // 判空
        if (StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        // 搜索文章
        List<Video> videoList = videoMapper.selectList(new LambdaQueryWrapper<Video>()
                .eq(Video::getIsDelete, FALSE)
                .and(i -> i.like(Video::getVideoName, keywords))
                .last("limit " + PageUtils.getLimitCurrent() + ", "
                        + PageUtils.getSize()));
        // 高亮处理
        List<VideoSearchDTO> videoSearchDTOList = videoList.stream().map(item -> {
            // 视频名称高亮
            String videoName = item.getVideoName()
                    .replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
            return VideoSearchDTO.builder()
                    .id(item.getId())
                    .videoName(videoName)
                    .videoCover(item.getVideoCover())
                    .playId(item.getPlayId())
                    .build();
        }).collect(Collectors.toList());
        videoSearchDTOList.forEach(item ->
                item.setDuration(vodUtils.getVideoDuration(item.getPlayId())));
        for (VideoSearchDTO videoSearchDTO : videoSearchDTOList) {
            // 封装点赞量和浏览量
            Double score = redisUtils.zScore(VIDEO_VIEW_COUNT, videoSearchDTO.getId());
            if (Objects.nonNull(score)) {
                videoSearchDTO.setViewCount(score.intValue());
            }
            videoSearchDTO.setLikeCount((Integer) redisUtils.hGet(VIDEO_LIKE_COUNT,
                    videoSearchDTO.getId().toString()));
        }
        return videoSearchDTOList;
    }

    @Override
    public VideoPreviewListDTO listVideosByCondition(ConditionVO condition) {
        // 查询视频
        List<VideoPreviewDTO> videoPreviewDTOList =
                videoMapper.listVideosByCondition(PageUtils.getLimitCurrent(),
                        PageUtils.getSize(), condition);
        // 搜索条件对应名(标签或分类名)
        String name;
        if (Objects.nonNull(condition.getCategoryId())) {
            name = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                    .select(Category::getCategoryName)
                    .eq(Category::getId, condition.getCategoryId()))
                    .getCategoryName();
        } else {
            name = tagService.getOne(new LambdaQueryWrapper<Tag>()
                    .select(Tag::getTagName)
                    .eq(Tag::getId, condition.getTagId()))
                    .getTagName();
        }
        return VideoPreviewListDTO.builder()
                .videoPreviewDTOList(videoPreviewDTOList)
                .name(name)
                .build();
    }

    @Override
    public List<VideoHomeDTO> listVideosByUserId(ConditionVO condition) {
        // 查询视频
        List<VideoPreviewDTO> videoPreviewDTOList =
                videoMapper.listVideosByCondition(PageUtils.getLimitCurrent(),
                        PageUtils.getSize(), condition);
        List<VideoHomeDTO> videoHomeDTOList =
                BeanCopyUtils.copyList(videoPreviewDTOList, VideoHomeDTO.class);
        videoHomeDTOList.forEach(item ->
                item.setDuration(vodUtils.getVideoDuration(item.getPlayId())));
        for (VideoHomeDTO videoHomeDTO : videoHomeDTOList) {
            // 封装点赞量和浏览量
            Double score = redisUtils.zScore(VIDEO_VIEW_COUNT, videoHomeDTO.getId());
            if (Objects.nonNull(score)) {
                videoHomeDTO.setViewCount(score.intValue());
            }
            videoHomeDTO.setLikeCount((Integer) redisUtils.hGet(VIDEO_LIKE_COUNT,
                    videoHomeDTO.getId().toString()));
        }
        return videoHomeDTOList;
    }

    @Override
    public String saveVideo(MultipartFile file) {
        return vodUtils.uploadFile(file);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateVideo(VideoVO videoVO) {
        // 保存视频分类
        Category category = saveVideoCategory(videoVO);
        // 保存或修改视频
        Video video = BeanCopyUtils.copyObject(videoVO, Video.class);
        if (Objects.nonNull(category)) {
            video.setCategoryId(category.getId());
        }
        video.setUserId(UserUtils.getLoginUser().getUserInfoId());
        this.saveOrUpdate(video);
        vodUtils.updateVideoInfo(videoVO);
        // 保存视频标签
        saveVideoTag(videoVO, video.getId());
    }

    @Override
    public VideoDTO getVideoById(Integer videoId) {
        // 查询推荐视频
        CompletableFuture<List<VideoRecommendDTO>> recommendVideoList =
                CompletableFuture.supplyAsync(() ->
                        videoMapper.listRecommendVideos(videoId));
        // 查询最新视频
        CompletableFuture<List<VideoRecommendDTO>> newestVideoList =
                CompletableFuture.supplyAsync(() -> {
                    List<Video> videoList =
                            videoMapper.selectList(new LambdaQueryWrapper<Video>()
                                    .select(Video::getId, Video::getVideoName,
                                            Video::getVideoCover, Video::getGmtCreate)
                                    .eq(Video::getIsDelete, FALSE)
                                    .orderByDesc(Video::getId)
                                    .last("limit 5"));
                    return BeanCopyUtils.copyList(videoList, VideoRecommendDTO.class);
                });
        // 查询id对应视频
        VideoDTO video = videoMapper.getArticleById(videoId);
        if (Objects.isNull(video)) {
            throw new ServiceException("视频不存在");
        }
        String url = vodUtils.getVideoUrl(video.getPlayId());
        video.setUrl(url);
        // 更新视频浏览量
        updateVideoViewsCount(videoId);
        // 封装点赞量和浏览量
        Double score = redisUtils.zScore(VIDEO_VIEW_COUNT, videoId);
        if (Objects.nonNull(score)) {
            video.setViewsCount(score.intValue());
        }
        video.setLikeCount((Integer) redisUtils.hGet(VIDEO_LIKE_COUNT, videoId.toString()));
        // 封装文章信息
        try {
            video.setRecommendVideoList(recommendVideoList.get());
            video.setNewestVideoList(newestVideoList.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return video;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveVideoLike(Integer videoId) {
        // 判断是否点赞
        String articleLikeKey = VIDEO_USER_LIKE + UserUtils.getLoginUser().getUserInfoId();
        if (redisUtils.sHasKey(articleLikeKey, videoId)) {
            // 点过赞则删除文章id
            redisUtils.sRem(articleLikeKey, videoId);
            // 文章点赞量-1
            redisUtils.hDecr(VIDEO_LIKE_COUNT, videoId.toString(), 1L);
        } else {
            // 未点赞则增加文章id
            redisUtils.sSet(articleLikeKey, videoId);
            // 文章点赞量+1
            redisUtils.hIncr(VIDEO_LIKE_COUNT, videoId.toString(), 1L);
        }
    }

    /**
     * 更新文章浏览量
     * @param videoId 视频id
     */
    @Async
    public void updateVideoViewsCount(Integer videoId) {
        // 判断是否第一次访问，增加浏览量
        Set<Integer> videoSet =
                (Set<Integer>) Optional.ofNullable(
                        session.getAttribute(VIDEO_SET)).orElse(new HashSet<>());
        if (!videoSet.contains(videoId)) {
            videoSet.add(videoId);
            session.setAttribute(VIDEO_SET, videoSet);
            // 浏览量+1
            redisUtils.zIncr(VIDEO_VIEW_COUNT, videoId, 1D);
        }
    }

    /**
     * 保存视频分类
     * @param videoVO 视频信息
     * @return {@link Category} 视频分类
     */
    private Category saveVideoCategory(VideoVO videoVO) {
        // 判断分类是否存在
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .eq(Category::getCategoryName, videoVO.getCategoryName()));
        if (Objects.isNull(category)) {
            throw new ServiceException("该分类不存在");
            //category = Category.builder()
            //        .categoryName(videoVO.getCategoryName())
            //        .build();
            //categoryMapper.insert(category);
        }
        return category;
    }

    /**
     * 保存视频标签
     * @param videoVO 视频信息
     */
    private void saveVideoTag(VideoVO videoVO, Integer videoId) {
        // 编辑视频则删除视频所有标签
        if (Objects.nonNull(videoVO.getId())) {
            videoTagMapper.delete(new LambdaQueryWrapper<VideoTag>()
                    .eq(VideoTag::getVideoId, videoVO.getId()));
        }
        // 添加视频标签
        List<String> tagNameList = videoVO.getTagNameList();
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 查询已存在的标签
            List<Tag> existTagList = tagService.list(new LambdaQueryWrapper<Tag>()
                    .in(Tag::getTagName, tagNameList));
            List<String> existTagNameList = existTagList.stream()
                    .map(Tag::getTagName)
                    .collect(Collectors.toList());
            List<Integer> existTagIdList = existTagList.stream()
                    .map(Tag::getId)
                    .collect(Collectors.toList());
            // 对比新增不存在的标签
            tagNameList.removeAll(existTagNameList);
            if (CollectionUtils.isNotEmpty(tagNameList)) {
                List<Tag> tagList = tagNameList.stream().map(item -> Tag.builder()
                        .tagName(item)
                        .build())
                        .collect(Collectors.toList());
                tagService.saveBatch(tagList);
                List<Integer> tagIdList = tagList.stream()
                        .map(Tag::getId)
                        .collect(Collectors.toList());
                existTagIdList.addAll(tagIdList);
            }
            // 提取标签id绑定视频
            List<VideoTag> articleTagList =
                    existTagIdList.stream().map(item -> VideoTag.builder()
                            .videoId(videoId)
                            .tagId(item)
                            .build())
                            .collect(Collectors.toList());
            videoTagService.saveBatch(articleTagList);
        }
    }

    @Override
    public PageResult<VideoBackDTO> listVideoBack(ConditionVO condition) {
        // 查询视频总量
        Integer count = videoMapper.countVideoBack(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台视频
        List<VideoBackDTO> videoBackList =
                videoMapper.listVideoBack(PageUtils.getLimitCurrent(),
                        PageUtils.getSize(), condition);
        // 查询视频点赞量和浏览量
        Map<Object, Double> viewCountMap = redisUtils.zAllScore(VIDEO_VIEW_COUNT);
        Map<Object, Object> likeCountMap = redisUtils.hmGet(VIDEO_LIKE_COUNT);
        // 封装点赞量和浏览量
        videoBackList.forEach(item -> {
            Double viewCount = viewCountMap.get(item.getId());
            if (Objects.nonNull(viewCount)) {
                item.setViewCount(viewCount.intValue());
            }
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
        });
        return new PageResult<>(videoBackList, count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateVideoDelete(DeleteVO delete) {
        // 修改视频逻辑删除状态
        List<Video> videoList = delete.getIdList().stream()
                .map(id -> Video.builder()
                        .id(id)
                        .isTop(FALSE)
                        .isDelete(delete.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(videoList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteVideos(List<Integer> videoIdList) {
        // 删除视频标签关联
        videoTagMapper.delete(new LambdaQueryWrapper<VideoTag>()
                .in(VideoTag::getVideoId, videoIdList));
        for (Integer videoId : videoIdList) {
            redisUtils.zRem(VIDEO_VIEW_COUNT, videoId);
        }
        // 删除视频
        videoMapper.deleteBatchIds(videoIdList);
    }

}
