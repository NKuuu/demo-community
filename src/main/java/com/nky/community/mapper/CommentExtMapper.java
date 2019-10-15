package com.nky.community.mapper;

import com.nky.community.entity.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment record);
}