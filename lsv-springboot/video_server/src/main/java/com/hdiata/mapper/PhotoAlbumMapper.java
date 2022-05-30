package com.hdiata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdiata.pojo.dto.PhotoAlbumBackDTO;
import com.hdiata.pojo.entity.PhotoAlbum;
import com.hdiata.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 相册
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/18 16:35
 */
@Repository
public interface PhotoAlbumMapper extends BaseMapper<PhotoAlbum> {

    /**
     * 查询后台相册列表
     * @param current 页码
     * @param size 大小
     * @param condition 条件
     * @return {@link List < PhotoAlbumBackDTO >} 相册列表
     */
    List<PhotoAlbumBackDTO> listPhotoAlbumBacks(@Param("current") Long current,
                                                @Param("size") Long size,
                                                @Param("condition") ConditionVO condition);

}
