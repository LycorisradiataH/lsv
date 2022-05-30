package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.PhotoBackDTO;
import com.hdiata.pojo.entity.Photo;
import com.hdiata.pojo.vo.*;

import java.util.List;

/**
 * 照片服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/18 16:12
 */
public interface PhotoService extends IService<Photo> {

    /**
     * 根据相册id获取照片列表
     * @param condition 条件
     * @return {@link PageResult<PhotoBackDTO>} 照片列表
     */
    PageResult<PhotoBackDTO> listPhotos(ConditionVO condition);

    /**
     * 更新照片信息
     * @param photoInfoVO 照片信息
     */
    void updatePhoto(PhotoInfoVO photoInfoVO);

    /**
     * 保存照片
     * @param photoVO 照片
     */
    void savePhotos(PhotoVO photoVO);

    /**
     * 移动照片相册
     * @param photoVO 照片信息
     */
    void updatePhotosAlbum(PhotoVO photoVO);

    /**
     * 更新照片删除状态
     * @param deleteVO 删除信息
     */
    void updatePhotoDelete(DeleteVO deleteVO);

    /**
     * 删除照片
     * @param photoIdList 照片id列表
     */
    void deletePhotos(List<Integer> photoIdList);
}
