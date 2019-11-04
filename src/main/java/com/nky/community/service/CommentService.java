package com.nky.community.service;

import com.nky.community.dto.CommentDTO;
import com.nky.community.entity.Comment;
import com.nky.community.entity.CommentExample;
import com.nky.community.entity.Notification;
import com.nky.community.entity.Question;
import com.nky.community.entity.User;
import com.nky.community.entity.UserExample;
import com.nky.community.enums.CommentTypeEnum;
import com.nky.community.enums.NotificationStatusEnum;
import com.nky.community.enums.NotificationTypeEnum;
import com.nky.community.exception.CustomizeErrorCode;
import com.nky.community.exception.CustomizeException;
import com.nky.community.mapper.CommentExtMapper;
import com.nky.community.mapper.CommentMapper;
import com.nky.community.mapper.NotificationMapper;
import com.nky.community.mapper.QuestionExtMapper;
import com.nky.community.mapper.QuestionMapper;
import com.nky.community.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Auther:nky
 * @Date:2019/10/12
 * @Description:com.nky.community.service
 * @version:1.0
 */
@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentExtMapper commentExtMapper;
    @Autowired
    NotificationMapper notificationMapper;

    /**
     * 保存数据
     *
     * @param comment
     */
    @Transactional
    public void insert(Comment comment, User commentator) {
        // 判断问题发布者Id是否为空
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        // 判断回复类型是否为空且该类型在CommentTypeEnum中是否存在
        if (comment.getType() == null || CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        // 如果是二级评论则执行
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            // 数据库总的评论记录
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            // 评论为空抛出异常
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            // 数据库总的问题记录
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            // 问题为空抛出异常
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            // 回复评论成功存入数据库
            commentMapper.insert(comment);

            // 增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);

            // 创建通知
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
        }
        // 如果是回复问题则执行
        else {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());

            // 回复问题出错抛出异常
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            // 回复问题成功存入数据库
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);

            // 创建通知
            createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }
    }

    /**
     * 通知
     *
     * @param comment
     * @param outerTitle
     * @param notificationType
     */
    private void createNotify(Comment comment, Long receriver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receriver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    /**
     * 根据问题id查找
     *
     * @param id
     * @param type
     * @return
     */
    public List<CommentDTO> listByQuestionId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取去重的评论人
        Set<Long> commentators = comments.stream()
                .map(comment -> comment.getCommentator())
                .collect(Collectors.toSet());

        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        // 获取评论人并转换为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    BeanUtils.copyProperties(comment, commentDTO);
                    commentDTO.setUser(userMap.get(comment.getCommentator()));
                    return commentDTO;
                })
                .collect(Collectors.toList());

        return commentDTOS;
    }
}
