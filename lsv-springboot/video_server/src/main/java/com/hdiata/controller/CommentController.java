package com.hdiata.controller;

import com.hdiata.common.annotation.OptLog;
import com.hdiata.pojo.dto.CommentBackDTO;
import com.hdiata.pojo.dto.CommentDTO;
import com.hdiata.pojo.dto.ReplyDTO;
import com.hdiata.pojo.vo.*;
import com.hdiata.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.hdiata.common.constant.OptTypeConst.REMOVE;
import static com.hdiata.common.constant.OptTypeConst.UPDATE;

/**
 * 评论控制器
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/27 17:37
 */
@Api(tags = "评论模块")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 查询评论
     * @param commentVO 评论信息
     * @return {@link Result<CommentDTO>}
     */
    @ApiOperation(value = "查询评论")
    @GetMapping("/comments")
    public Result<PageResult<CommentDTO>> listComments(CommentVO commentVO) {
        return Result.success(commentService.listComments(commentVO));
    }

    /**
     * 添加评论
     * @param commentVO 评论信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "添加评论")
    @PostMapping("/comments")
    public Result<?> saveComment(@Valid @RequestBody CommentVO commentVO) {
        commentService.saveComment(commentVO);
        return Result.success();
    }

    /**
     * 查询评论下的回复
     * @param commentId 评论id
     * @return {@link Result<ReplyDTO>} 回复列表
     */
    @ApiOperation(value = "查询评论下的回复")
    @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataType = "Integer")
    @GetMapping("/comments/{commentId}/replies")
    public Result<List<ReplyDTO>> listRepliesByCommentId(@PathVariable("commentId") Integer commentId) {
        return Result.success(commentService.listRepliesByCommentId(commentId));
    }

    /**
     * 评论点赞
     * @param commentId 评论id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "评论点赞")
    @PostMapping("/comments/{commentId}/like")
    public Result<?> saveCommentLike(@PathVariable("commentId") Integer commentId) {
        commentService.saveCommentLike(commentId);
        return Result.success();
    }

    /**
     * 查询后台评论
     * @param condition 查询条件
     * @return {@link Result<CommentBackDTO>} 后台评论
     */
    @ApiOperation(value = "查询后台评论")
    @GetMapping("/admin/comments")
    public Result<PageResult<CommentBackDTO>> listCommentBack(ConditionVO condition) {
        return Result.success(commentService.listCommentBack(condition));
    }

    /**
     * 审核评论
     * @param reviewVO 审核信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @ApiOperation(value = "审核评论")
    @PutMapping("/admin/comments/review")
    public Result<?> updateCommentsReview(@Valid @RequestBody ReviewVO reviewVO) {
        commentService.updateCommentsReview(reviewVO);
        return Result.success();
    }

    /**
     * 删除评论
     * @param commentIdList 评论id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除评论")
    @DeleteMapping("/admin/comments")
    public Result<?> deleteComments(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.success();
    }

}
