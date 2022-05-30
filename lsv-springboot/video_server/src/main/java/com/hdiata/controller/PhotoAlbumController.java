package com.hdiata.controller;

import com.hdiata.common.annotation.OptLog;
import com.hdiata.common.enums.FilePathEnum;
import com.hdiata.common.strategy.context.UploadStrategyContext;
import com.hdiata.pojo.dto.PhotoAlbumBackDTO;
import com.hdiata.pojo.dto.PhotoAlbumDTO;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.PhotoAlbumVO;
import com.hdiata.pojo.vo.Result;
import com.hdiata.service.PhotoAlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.hdiata.common.constant.OptTypeConst.REMOVE;
import static com.hdiata.common.constant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * 相册控制器
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/18 16:33
 */
@Api(tags = "相册模块")
@RestController
public class PhotoAlbumController {

    @Autowired
    private PhotoAlbumService photoAlbumService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    /**
     * 上传相册封面
     * @param file 文件
     * @return {@link Result<String>} 相册封面地址
     */
    @ApiOperation(value = "上传相册封面")
    @ApiImplicitParam(name = "file", value = "相册封面", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/photos/albums/cover")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.success(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath()));
    }

    /**
     * 查看后台相册列表
     * @param condition 条件
     * @return {@link Result<PhotoAlbumBackDTO>} 相册列表
     */
    @ApiOperation(value = "查看后台相册列表")
    @GetMapping("/admin/photos/albums")
    public Result<PageResult<PhotoAlbumBackDTO>> listPhotoAlbumBacks(ConditionVO condition) {
        return Result.success(photoAlbumService.listPhotoAlbumBacks(condition));
    }

    /**
     * 获取后台相册列表信息
     * @return {@link Result<PhotoAlbumDTO>} 相册列表信息
     */
    @ApiOperation(value = "获取后台相册列表信息")
    @GetMapping("/admin/photos/albums/info")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbumBackInfos() {
        return Result.success(photoAlbumService.listPhotoAlbumBackInfos());
    }

    /**
     * 保存或更新相册
     * @param photoAlbumVO 相册信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新相册")
    @PostMapping("/admin/photos/albums")
    public Result<?> saveOrUpdatePhotoAlbum(@Valid @RequestBody PhotoAlbumVO photoAlbumVO) {
        photoAlbumService.saveOrUpdatePhotoAlbum(photoAlbumVO);
        return Result.success();
    }

    /**
     * 根据id获取后台相册信息
     * @param albumId 相册id
     * @return {@link Result}相册信息
     */
    @ApiOperation(value = "根据id获取后台相册信息")
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "Integer")
    @GetMapping("/admin/photos/albums/{albumId}/info")
    public Result<PhotoAlbumBackDTO> getPhotoAlbumBackById(@PathVariable("albumId") Integer albumId) {
        return Result.success(photoAlbumService.getPhotoAlbumBackById(albumId));
    }

    /**
     * 根据id删除相册
     * @param albumId 相册id
     * @return {@link Result}
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "根据id删除相册")
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "Integer")
    @DeleteMapping("/admin/photos/albums/{albumId}")
    public Result<?> deletePhotoAlbumById(@PathVariable("albumId") Integer albumId) {
        photoAlbumService.deletePhotoAlbumById(albumId);
        return Result.success();
    }

}
