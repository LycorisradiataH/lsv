package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.PhotoAlbumBackDTO;
import com.hdiata.pojo.dto.PhotoAlbumDTO;
import com.hdiata.pojo.entity.PhotoAlbum;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.PhotoAlbumVO;

import java.util.List;

/**
 * 相册服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/18 16:34
 */
public interface PhotoAlbumService extends IService<PhotoAlbum> {

    /**
     * 查看后台相册列表
     * @param condition 条件
     * @return {@link PageResult<PhotoAlbumBackDTO>} 相册列表
     */
    PageResult<PhotoAlbumBackDTO> listPhotoAlbumBacks(ConditionVO condition);

    /**
     * 获取后台相册列表信息
     * @return {@link List<PhotoAlbumDTO>} 相册列表信息
     */
    List<PhotoAlbumDTO> listPhotoAlbumBackInfos();

    /**
     * 保存或更新相册
     * @param photoAlbumVO 相册信息
     */
    void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO);

    /**
     * 根据id获取相册信息
     * @param albumId 相册id
     * @return {@link PhotoAlbumBackDTO} 相册信息
     */
    PhotoAlbumBackDTO getPhotoAlbumBackById(Integer albumId);

    /**
     * 根据id删除相册
     * @param albumId 相册id
     */
    void deletePhotoAlbumById(Integer albumId);
}
