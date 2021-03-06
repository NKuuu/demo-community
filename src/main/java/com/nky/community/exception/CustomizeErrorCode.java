package com.nky.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    /**
     * 错误类型
     */
    QUESTION_NOT_FOUND(2001, "你要找的问题不在了，要不要换个试试~"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登录后重试！"),
    SYS_ERROR(2004, "服务器冒烟了，要不稍后再试试~"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在！"),
    COMMENT_NOT_FOUND(2006, "你操作的评论不存在"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "不能读取别人的信息"),
    NOTIFICATION_NOT_FOUND(2009, "当前消息不存在");


    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
