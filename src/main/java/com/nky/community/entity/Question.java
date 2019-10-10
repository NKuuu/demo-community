package com.nky.community.entity;

import lombok.Data;

/**
 * @Auther:nky
 * @Date:2019/10/10
 * @Description:com.nky.community.entity
 * @version:1.0
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
}
