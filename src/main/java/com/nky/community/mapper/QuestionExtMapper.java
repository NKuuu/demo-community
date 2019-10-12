package com.nky.community.mapper;

import com.nky.community.entity.Question;
import com.nky.community.entity.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
}