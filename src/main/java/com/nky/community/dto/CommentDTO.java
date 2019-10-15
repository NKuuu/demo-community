package com.nky.community.dto;

import com.nky.community.entity.User;
import lombok.Data;

/**
 * @Auther:nky
 * @Date:2019/10/13
 * @Description:com.nky.community.dto
 * @version:1.0
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}
