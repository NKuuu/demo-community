package com.nky.community.exception;

/**
 * @Auther:nky
 * @Date:2019/10/12
 * @Description:com.nky.community.exception
 * @version:1.0
 */
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }

}
