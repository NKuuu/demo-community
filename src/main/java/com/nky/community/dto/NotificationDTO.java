package com.nky.community.dto;

import com.nky.community.entity.User;
import lombok.Data;

/**
 * @Auther:nky
 * @Date:2019/10/16
 * @Description:com.nky.community.dto
 * @version:1.0
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerId;
    private String typeName;
    private Integer type;
}
