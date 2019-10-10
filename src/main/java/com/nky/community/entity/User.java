package com.nky.community.entity;


import lombok.Data;

/**
 * @Auther:nky
 * @Date:2019/10/10
 * @Description:com.nky.community.entity
 * @version:1.0
 */
@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarURL;

}
