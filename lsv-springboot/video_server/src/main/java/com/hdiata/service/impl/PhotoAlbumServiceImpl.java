package com.hdiata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.common.exception.ServiceException;
import com.hdiata.mapper.PhotoAlbumMapper;
import com.hdiata.mapper.PhotoMapper;
import com.hdiata.pojo.dto.PhotoAlbumBackDTO;
import com.hdiata.pojo.dto.PhotoAlbumDTO;
import com.hdiata.pojo.entity.Photo;
import com.hdiata.pojo.entity.PhotoAlbum;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.PhotoAlbumVO;
import com.hdiata.service.PhotoAlbumService;
import com.hdiata.util.BeanCopyUtils;
import com.hdiata.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.hdiata.common.constant.CommonConst.FALSE;
import static com.hdiata.common.constant.CommonConst.TRUE;

/**
 * 相册服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/18 16:34
 */
@Service
public class PhotoAlbumServiceImpl extends ServiceImpl<PhotoAlbumMapper, PhotoAlbum> implements PhotoAlbumService {

    @Autowired
    private PhotoAlbumMapper photoAlbumMapper;

    @Autowired
    private PhotoMapper photoMapper;

    @Override
    public PageResult<PhotoAlbumBackDTO> listPhotoAlbumBacks(ConditionVO condition) {
        // 查询相册数量
        Integer count = photoAlbumMapper.selectCount(new LambdaQueryWrapper<PhotoAlbum>()
                .like(StringUtils.isNotBlank(condition.getKeywords()),
                        PhotoAlbum::getAlbumName, condition.getKeywords())
                .eq(PhotoAlbum::getIsDelete, FALSE));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询相册信息
        List<PhotoAlbumBackDTO> photoAlbumBackList =
                photoAlbumMapper.listPhotoAlbumBacks(PageUtils.getLimitCurrent(),
                        PageUtils.getSize(), condition);
        return new PageResult<>(photoAlbumBackList, count);
    }

    @Override
    public List<PhotoAlbumDTO> listPhotoAlbumBackInfos() {
        List<PhotoAlbum> photoAlbumList =
                photoAlbumMapper.selectList(new LambdaQueryWrapper<PhotoAlbum>()
                        .eq(PhotoAlbum::getIsDelete, FALSE));
        return BeanCopyUtils.copyList(photoAlbumList, PhotoAlbumDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO) {
        // 查询相册名是否存在
        PhotoAlbum album = photoAlbumMapper.selectOne(new LambdaQueryWrapper<PhotoAlbum>()
                .select(PhotoAlbum::getId)
                .eq(PhotoAlbum::getAlbumName, photoAlbumVO.getAlbumName()));
        if (Objects.nonNull(album) && !album.getId().equals(photoAlbumVO.getId())) {
            throw new ServiceException("相册名已存在");
        }
        PhotoAlbum photoAlbum = BeanCopyUtils.copyObject(photoAlbumVO, PhotoAlbum.class);
        this.saveOrUpdate(photoAlbum);
    }

    @Override
    public PhotoAlbumBackDTO getPhotoAlbumBackById(Integer albumId) {
        // 查询相册信息
        PhotoAlbum photoAlbum = photoAlbumMapper.selectById(albumId);
        // 查询照片数量
        Integer photoCount = photoMapper.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId)
                .eq(Photo::getIsDelete, FALSE));
        PhotoAlbumBackDTO album = BeanCopyUtils.copyObject(photoAlbum, PhotoAlbumBackDTO.class);
        album.setPhotoCount(photoCount);
        return album;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePhotoAlbumById(Integer albumId) {
        // 查询照片数量
        Integer count = photoMapper.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));
        if (count > 0) {
            // 若相册下存在照片则逻辑删除相册和照片
            photoAlbumMapper.updateById(PhotoAlbum.builder()
                    .id(albumId)
                    .isDelete(TRUE)
                    .build());
            photoMapper.update(new Photo(), new LambdaUpdateWrapper<Photo>()
                    .set(Photo::getIsDelete, TRUE)
                    .eq(Photo::getAlbumId, albumId));
        } else {
            // 若相册下不存在照片则直接删除
            photoAlbumMapper.deleteById(albumId);
        }
    }

}
