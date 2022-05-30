package com.hdiata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.mapper.VideoTagMapper;
import com.hdiata.pojo.entity.VideoTag;
import com.hdiata.service.VideoTagService;
import org.springframework.stereotype.Service;

/**
 * 视频标签服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:52
 */
@Service
public class VideoTagServiceImpl extends ServiceImpl<VideoTagMapper, VideoTag> implements VideoTagService {
}
