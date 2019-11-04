package com.nky.community.enums;

/**
 * @Auther:nky
 * @Date:2019/10/16
 * @Description:com.nky.community.enums
 * @version:1.0
 */
public enum NotificationStatusEnum {

    /**
     * 通知状态
     */
    UNREAD(0), READ(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }}
