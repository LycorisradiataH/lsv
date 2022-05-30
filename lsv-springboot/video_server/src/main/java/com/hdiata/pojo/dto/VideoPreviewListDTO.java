package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 视频预览列表
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/30 16:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoPreviewListDTO {

    /**
     * 视频列表
     */
    private List<VideoPreviewDTO> videoPreviewDTOList;

    /**
     * 条件名
     */
    private String name;

}
