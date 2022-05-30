package com.hdiata.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import com.hdiata.common.exception.ServiceException;
import com.hdiata.config.OssConfigProperties;
import com.hdiata.pojo.vo.VideoVO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 视频上传工具类
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/30 17:48
 */
@Component
public class VodUtils {

    @Autowired
    private OssConfigProperties ossConfigProperties;

    /**
     * 上传文件
     * @param file 文件
     * @throws IOException 异常
     */
    public String uploadFile(MultipartFile file) {
        try {
            // 初始化VOD客户端并获取上传地址和凭证
            DefaultAcsClient vodClient = initVodClient();
            CreateUploadVideoResponse createUploadVideoResponse =
                    createUploadVideo(file.getOriginalFilename(), vodClient);
            // 执行成功会返回VideoId、UploadAddress和UploadAuth
            String videoId = createUploadVideoResponse.getVideoId();
            JSONObject uploadAuth = JSONObject.parseObject(
                    decodeBase64(createUploadVideoResponse.getUploadAuth()));
            JSONObject uploadAddress = JSONObject.parseObject(
                    decodeBase64(createUploadVideoResponse.getUploadAddress()));
            // 使用UploadAuth和UploadAddress初始化OSS客户端
            OSSClient ossClient = initOssClient(uploadAuth, uploadAddress);
            // 上传文件，注意是同步上传会阻塞等待，耗时与文件大小和网络上行带宽有关
            String bucketName = uploadAddress.getString("Bucket");
            String objectName = uploadAddress.getString("FileName");
            ossClient.putObject(bucketName, objectName, file.getInputStream());
            return videoId;
        } catch (Exception e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    /**
     * 修改视频信息
     * @param videoVO 视频信息
     */
    public void updateVideoInfo(VideoVO videoVO) {
        UpdateVideoInfoResponse response = new UpdateVideoInfoResponse();
        try {
            response = updateVodVideoInfo(videoVO);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 获取播放路径
     */
    public String getVideoUrl(String videoId) {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        GetPlayInfoResponse response = null;
        String url = null;
        try {
            response = initVodClient().getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                url = playInfo.getPlayURL();
            }
            return url;
        } catch (ClientException e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 获取播放时长
     */
    public Integer getVideoDuration(String videoId) {
        DefaultAcsClient client = initVodClient();
        GetVideoInfoResponse response = null;
        try {
            response = getVideoInfo(client, videoId);
            // 播放时长
            Float time = response.getVideo().getDuration();
            return Math.round(time);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 获取视频信息
     * @param client 发送请求客户端
     * @return GetVideoInfoResponse 获取视频信息响应数据
     * @throws Exception
     */
    public static GetVideoInfoResponse getVideoInfo(DefaultAcsClient client, String videoId) throws Exception {
        GetVideoInfoRequest request = new GetVideoInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    /**
     * 修改视频信息
     * @param videoVO 视频信息
     * @return UpdateVideoInfoResponse 修改视频信息响应数据
     * @throws Exception 异常
     */
    private UpdateVideoInfoResponse updateVodVideoInfo(VideoVO videoVO) throws Exception {
        DefaultAcsClient client = initVodClient();
        UpdateVideoInfoRequest request = new UpdateVideoInfoRequest();
        request.setVideoId(videoVO.getPlayId());
        request.setTitle(videoVO.getVideoName());
        request.setCoverURL(videoVO.getVideoCover());
        client.getAcsResponse(request);
        return client.getAcsResponse(request);
    }

    /**
     * 初始化vod客户端
     * @return vod客户端
     */
    private DefaultAcsClient initVodClient() {
        DefaultProfile profile = DefaultProfile.getProfile(ossConfigProperties.getRegionId(),
                ossConfigProperties.getAccessKeyId(),
                ossConfigProperties.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }

    /**
     * 创建上传视频响应
     * @param fileName 文件名
     * @param vodClient 客户端
     * @return 视频响应
     * @throws ClientException 异常
     */
    private CreateUploadVideoResponse createUploadVideo(String fileName, DefaultAcsClient vodClient) throws ClientException {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        String title = fileName.substring(0, fileName.lastIndexOf("."));
        String newFilename = UUID.randomUUID() + "."
                + StringUtils.substringAfterLast(fileName, ".");
        request.setFileName(newFilename);
        request.setTitle(title);
        request.setSysReadTimeout(1000);
        request.setSysConnectTimeout(1000);
        return vodClient.getAcsResponse(request);
    }

    /**
     * 初始化oss客户端
     * @param uploadAuth 上传密钥
     * @param uploadAddress 上传地址
     * @return oss客户端
     */
    private OSSClient initOssClient(JSONObject uploadAuth, JSONObject uploadAddress) {
        String endpoint = uploadAddress.getString("Endpoint");
        String accessKeyId = uploadAuth.getString("AccessKeyId");
        String accessKeySecret = uploadAuth.getString("AccessKeySecret");
        String securityToken = uploadAuth.getString("SecurityToken");
        return new OSSClient(endpoint, accessKeyId, accessKeySecret, securityToken);
    }

    /**
     * Base64解码
     * @param data 数据
     * @return 解码后的数据
     */
    private String decodeBase64(String data) {
        return new String(Base64.decodeBase64(data));
    }

}
