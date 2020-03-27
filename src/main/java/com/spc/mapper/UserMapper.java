package com.spc.mapper;

import com.spc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {




    @Select("SELECT * FROM user WHERE id = #{id}")
    User findUserById(@Param("id") Integer id);


    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserByUsernameAndPassword(@Param("username") String username,
                                      @Param("password") String password);
}
