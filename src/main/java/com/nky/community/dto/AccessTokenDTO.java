package com.nky.community.dto;

import lombok.Data;

/**
 * @Auther:nky
 * @Date:2019/10/9
 * @Description:com.nky.community.dto
 * @version:1.0
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
