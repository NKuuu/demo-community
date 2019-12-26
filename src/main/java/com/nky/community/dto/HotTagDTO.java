package com.nky.community.dto;

import lombok.Data;

/**
 * @Auther:nky
 * @Date:2019/11/4
 * @Description:com.nky.community.dto
 * @version:1.0
 */
@Data
public class HotTagDTO implements Comparable{
    private String name;
    private Integer priority;

    /**
     * 通过重写比较器指定排序规则为降序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
