package com.nky.community.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:nky
 * @Date:2019/10/11
 * @Description:com.nky.community.dto
 * @version:1.0
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private Boolean showPrevious;
    private Boolean showNext;
    private Boolean showFirstPage;
    private Boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        if (page != 1) {
            showPrevious = true;
        }
        if (page != totalPage) {
            showNext = true;
        }
        if (!pages.contains(1)) {
            showFirstPage = true;
        }
        if (!pages.contains(totalPage)) {
            showEndPage = true;
        }
    }
}
