package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/22 11:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDTO {

    /**
     * 照片列表
     */
    private List<String> photoList;

}
