package com.nky.community.exception;

/**
 * @Auther:nky
 * @Date:2019/10/12
 * @Description:com.nky.community.exception
 * @version:1.0
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
    public Integer getCode() {
        return code;
    }
}
