package com.nky.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @Auther:nky
 * @Date:2019/10/15
 * @Description:com.nky.community.dto
 * @version:1.0
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
