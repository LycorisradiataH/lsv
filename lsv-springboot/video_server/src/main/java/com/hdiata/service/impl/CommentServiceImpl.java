package com.hdiata.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdiata.mapper.CommentMapper;
import com.hdiata.mapper.UserInfoMapper;
import com.hdiata.mapper.VideoMapper;
import com.hdiata.pojo.dto.*;
import com.hdiata.pojo.entity.Comment;
import com.hdiata.pojo.vo.*;
import com.hdiata.service.CommentService;
import com.hdiata.service.VideoInfoService;
import com.hdiata.util.HTMLUtils;
import com.hdiata.util.PageUtils;
import com.hdiata.util.RedisUtils;
import com.hdiata.util.UserUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.hdiata.common.constant.CommonConst.FALSE;
import static com.hdiata.common.constant.CommonConst.TRUE;
import static com.hdiata.common.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.hdiata.common.constant.RedisPrefixConst.COMMENT_LIKE_COUNT;
import static com.hdiata.common.constant.RedisPrefixConst.COMMENT_USER_LIKE;

/**
 * 评论服务
 * @author Lin Hua
 * @version 1.0
 * @date 2022/2/20 16:41
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private VideoInfoService videoInfoService;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 网站网址
     */
    @Value("${website.url}")
    private String websiteUrl;

    @Override
    public PageResult<CommentDTO> listComments(CommentVO commentVO) {
        // 查询评论量
        Integer commentCount = commentMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getVideoId, commentVO.getVideoId())
                .isNull(Comment::getParentId)
                .eq(Comment::getIsReview, TRUE));
        if (commentCount == 0) {
            return new PageResult<>();
        }
        // 分页查询评论集合
        List<CommentDTO> commentDTOList =
                commentMapper.listComments(PageUtils.getLimitCurrent(),
                        PageUtils.getSize(), commentVO);
        if (CollectionUtils.isEmpty(commentDTOList)) {
            return new PageResult<>();
        }
        // 查询redis的评论点赞数据
        Map<Object, Object> likeCountMap = redisUtils.hmGet(COMMENT_LIKE_COUNT);
        // 提取评论id集合
        List<Integer> commentIdList = commentDTOList.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        // 根据评论id集合查询回复数据
        List<ReplyDTO> replyDTOList = commentMapper.listReplies(commentIdList);
        // 封装回复点赞量
        replyDTOList.forEach(item ->
                item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        // 根据评论id分组回复数据
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOList.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        // 根据评论id查询回复量
        Map<Integer, Integer> replyCountMap =
                commentMapper.listReplyCountByCommentId(commentIdList)
                        .stream()
                        .collect(Collectors.toMap(ReplyCountDTO::getCommentId,
                                ReplyCountDTO::getReplyCount));
        // 封装评论数据
        commentDTOList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
            item.setReplyDTOList(replyMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });
        return new PageResult<>(commentDTOList, commentCount);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveComment(CommentVO commentVO) {
        // 判断是否需要审核
        WebsiteConfigVO websiteConfig = videoInfoService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();
        // 过滤标签
        commentVO.setCommentContent(HTMLUtils.deleteTag(commentVO.getCommentContent()));
        Comment comment = Comment.builder()
                .userId(UserUtils.getLoginUser().getUserInfoId())
                .replyUserId(commentVO.getReplyUserId())
                .videoId(commentVO.getVideoId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .type(commentVO.getType())
                .isReview(isReview == TRUE ? FALSE : TRUE)
                .build();
        commentMapper.insert(comment);
        // 判断是否开启邮箱通知,通知用户
        CompletableFuture.runAsync(() -> {
            if (websiteConfig.getIsEmailNotice().equals(TRUE)) {
                notice(comment);
            }
        });
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId) {
        // 转换页码查询评论下的回复
        List<ReplyDTO> replyDTOList =
                commentMapper.listRepliesByCommentId(PageUtils.getLimitCurrent(),
                        PageUtils.getSize(), commentId);
        // 查询redis的评论点赞数据
        Map<Object, Object> likeCountMap = redisUtils.hmGet(COMMENT_LIKE_COUNT);
        // 封装点赞数据
        replyDTOList.forEach(item ->
                item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        return replyDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCommentLike(Integer commentId) {
        // 判断是否点赞
        String commentLikeKey = COMMENT_USER_LIKE + UserUtils.getLoginUser().getUserInfoId();
        if (redisUtils.sHasKey(commentLikeKey, commentId)) {
            // 点过赞则删除评论id
            redisUtils.sRem(commentLikeKey, commentId);
            // 评论点赞量-1
            redisUtils.hDecr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        } else {
            // 未点赞则增加评论id
            redisUtils.sSet(commentLikeKey, commentId);
            // 评论点赞量+1
            redisUtils.hIncr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }

    /**
     * 通知评论用户
     * @param comment 评论信息
     */
    public void notice(Comment comment) {
        // 查询回复用户邮箱号
        Integer userId = 1;
        String id = "";
        userId = videoMapper.selectById(comment.getVideoId()).getUserId();
        id = comment.getVideoId().toString();
        if (Objects.nonNull(comment.getReplyUserId())) {
            userId = comment.getReplyUserId();
        }
        String email = userInfoMapper.selectById(userId).getEmail();
        if (StringUtils.isNotBlank(email)) {
            // 发送消息
            EmailDTO emailDTO = new EmailDTO();
            if (comment.getIsReview().equals(TRUE)) {
                // 评论提醒
                emailDTO.setEmail(email);
                emailDTO.setSubject("评论提醒");
                // 获取评论路径
                String url = websiteUrl + "/videos/" + id;
                emailDTO.setContent("您收到了一条新的回复，请前往" + url + "\n页面查看");
            } else {
                // 管理员审核提醒
                String adminEmail = userInfoMapper.selectById(1).getEmail();
                emailDTO.setEmail(adminEmail);
                emailDTO.setSubject("审核提醒");
                emailDTO.setContent("您收到了一条新的回复，请前往后台管理页面审核");
            }
            rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        }
    }

    @Override
    public PageResult<CommentBackDTO> listCommentBack(ConditionVO condition) {
        // 统计后台评论量
        Integer count = commentMapper.countComment(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台评论集合
        List<CommentBackDTO> commentBackList =
                commentMapper.listCommentBack(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(commentBackList, count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommentsReview(ReviewVO reviewVO) {
        // 修改评论审核状态
        List<Comment> commentList = reviewVO.getIdList().stream().map(item -> Comment.builder()
                .id(item)
                .isReview(reviewVO.getIsReview())
                .build())
                .collect(Collectors.toList());
        this.updateBatchById(commentList);
    }
}
