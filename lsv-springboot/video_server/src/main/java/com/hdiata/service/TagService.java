package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.TagBackDTO;
import com.hdiata.pojo.dto.TagDTO;
import com.hdiata.pojo.entity.Tag;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.TagVO;

import java.util.List;

/**
 * 标签服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:44
 */
public interface TagService extends IService<Tag> {

    /**
     * 查询后台标签
     * @param condition 查询条件
     * @return {@link PageResult<TagBackDTO>} 标签列表
     */
    PageResult<TagBackDTO> listTagsBack(ConditionVO condition);

    /**
     * 搜索视频标签
     * @param condition 查询条件
     * @return {@link List<TagDTO>} 标签列表
     */
    List<TagDTO> listTagsBySearch(ConditionVO condition);

    /**
     * 添加或更新标签
     * @param tagVO 标签
     */
    void saveOrUpdateTag(TagVO tagVO);

    /**
     * 删除标签
     * @param tagIdList 标签id集合
     */
    void deleteTags(List<Integer> tagIdList);
}
