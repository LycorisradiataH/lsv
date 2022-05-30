package com.hdiata.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hdiata.pojo.dto.CommentBackDTO;
import com.hdiata.pojo.dto.CommentDTO;
import com.hdiata.pojo.dto.ReplyDTO;
import com.hdiata.pojo.entity.Comment;
import com.hdiata.pojo.vo.CommentVO;
import com.hdiata.pojo.vo.ConditionVO;
import com.hdiata.pojo.vo.PageResult;
import com.hdiata.pojo.vo.ReviewVO;

import java.util.List;

/**
 * 评论服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:41
 */
public interface CommentService extends IService<Comment> {

    /**
     * 查看评论
     * @param commentVO 评论信息
     * @return 评论列表
     */
    PageResult<CommentDTO> listComments(CommentVO commentVO);

    /**
     * 添加评论
     * @param commentVO 评论对象
     */
    void saveComment(CommentVO commentVO);

    /**
     * 查看评论下的回复
     * @param commentId 评论id
     * @return 回复列表
     */
    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    /**
     * 点赞评论
     * @param commentId 评论id
     */
    void saveCommentLike(Integer commentId);

    /**
     * 查询后台评论
     * @param condition 查询条件
     * @return 评论列表
     */
    PageResult<CommentBackDTO> listCommentBack(ConditionVO condition);

    /**
     * 审核评论
     * @param reviewVO 审核信息
     */
    void updateCommentsReview(ReviewVO reviewVO);

}
