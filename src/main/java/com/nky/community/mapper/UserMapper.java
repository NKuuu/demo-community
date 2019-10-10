package com.nky.community.mapper;

import com.nky.community.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Auther:nky
 * @Date:2019/10/10
 * @Description:com.nky.community.mapper
 * @version:1.0
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    public void insert(User user);

    @Select("select * from user where token=#{token}")
    public User findByToken(@Param("token") String token);
}
