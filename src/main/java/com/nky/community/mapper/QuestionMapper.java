package com.nky.community.mapper;

import com.nky.community.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther:nky
 * @Date:2019/10/10
 * @Description:com.nky.community.mapper
 * @version:1.0
 */
public interface QuestionMapper {

    @Insert(
            "insert into question " +
                "(title,description,gmt_create,gmt_modified,creator,tag) " +
            "values" +
                "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    @Select("select * from question")
    List<Question> list();
}
