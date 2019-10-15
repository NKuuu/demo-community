package com.nky.community.dto;

import lombok.Data;

/**
 * @Auther:nky
 * @Date:2019/10/12
 * @Description:com.nky.community.dto
 * @version:1.0
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
