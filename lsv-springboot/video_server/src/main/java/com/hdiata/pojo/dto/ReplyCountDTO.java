package com.hdiata.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回复数量
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/31 21:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyCountDTO {

    /**
     * 评论id
     */
    private Integer commentId;

    /**
     * 回复数量
     */
    private Integer replyCount;

}
