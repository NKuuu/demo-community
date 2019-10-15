package com.nky.community.enums;

/**
 * @Auther:nky
 * @Date:2019/10/12
 * @Description:com.nky.community.enums
 * @version:1.0
 */
public enum CommentTypeEnum {
    /**
     *  评论类型
     */
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType().equals(type)) {
                return false;
            }
        }
        return true;
    }
}
