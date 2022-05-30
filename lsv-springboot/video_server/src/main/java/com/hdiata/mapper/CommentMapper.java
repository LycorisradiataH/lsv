package com.hdiata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdiata.pojo.dto.CommentBackDTO;
import com.hdiata.pojo.dto.CommentDTO;
import com.hdiata.pojo.dto.ReplyCountDTO;
import com.hdiata.pojo.dto.ReplyDTO;
import com.hdiata.pojo.entity.Comment;
import com.hdiata.pojo.vo.CommentVO;
import com.hdiata.pojo.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:41
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查看评论
     * @param current 页码
     * @param size 大小
     * @param commentVO 评论信息
     * @return 评论集合
     */
    List<CommentDTO> listComments(@Param("current") Long current,
                                  @Param("size") Long size,
                                  @Param("commentVO") CommentVO commentVO);

    /**
     * 查看评论id集合下的回复
     * @param commentIdList 评论id集合
     * @return 回复集合
     */
    List<ReplyDTO> listReplies(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 根据评论id查询回复总量
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 查看当条评论下的回复
     * @param current 页码
     * @param size 大小
     * @param commentId 评论id
     * @return 回复集合
     */
    List<ReplyDTO> listRepliesByCommentId(@Param("current") Long current,
                                          @Param("size") Long size,
                                          @Param("commentId") Integer commentId);

    /**
     * 统计后台评论数量
     * @param condition 查询条件
     * @return 评论数量
     */
    Integer countComment(@Param("condition") ConditionVO condition);

    /**
     * 查询后台评论
     * @param current 页码
     * @param size 大小
     * @param condition 查询条件
     * @return 评论集合
     */
    List<CommentBackDTO> listCommentBack(@Param("current") Long current,
                                         @Param("size") Long size,
                                         @Param("condition") ConditionVO condition);

}
