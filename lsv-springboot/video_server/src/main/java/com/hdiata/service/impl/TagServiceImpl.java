package com.hdiata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.common.exception.ServiceException;
import com.hdiata.mapper.TagMapper;
import com.hdiata.mapper.VideoTagMapper;
import com.hdiata.pojo.dto.TagBackDTO;
import com.hdiata.pojo.dto.TagDTO;
import com.hdiata.pojo.entity.Tag;
import com.hdiata.pojo.entity.VideoTag;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.TagVO;
import com.hdiata.service.TagService;
import com.hdiata.util.BeanCopyUtils;
import com.hdiata.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 标签服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:44
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private VideoTagMapper videoTagMapper;

    @Override
    public PageResult<TagBackDTO> listTagsBack(ConditionVO condition) {
        // 查询标签数量
        Integer count = tagMapper.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Tag::getTagName, condition.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询标签列表
        List<TagBackDTO> tagBackList =
                tagMapper.listTagsBack(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(tagBackList, count);
    }

    @Override
    public List<TagDTO> listTagsBySearch(ConditionVO condition) {
        // 搜索标签
        List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Tag::getTagName, condition.getKeywords())
                .orderByDesc(Tag::getId));
        return BeanCopyUtils.copyList(tagList, TagDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateTag(TagVO tagVO) {
        // 查询标签名是否存在
        Tag existTag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId)
                .eq(Tag::getTagName, tagVO.getTagName()));
        if (Objects.nonNull(existTag) && !existTag.getId().equals(tagVO.getId())) {
            throw new ServiceException("标签名已存在");
        }
        Tag tag = BeanCopyUtils.copyObject(tagVO, Tag.class);
        this.saveOrUpdate(tag);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTags(List<Integer> tagIdList) {
        // 删除标签和视频关联数据
        videoTagMapper.delete(new LambdaQueryWrapper<VideoTag>().in(VideoTag::getTagId, tagIdList));
        tagMapper.deleteBatchIds(tagIdList);
    }
}
