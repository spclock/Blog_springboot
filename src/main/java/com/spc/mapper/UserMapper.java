package com.spc.mapper;

import com.spc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {


    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserById(@Param("id") Integer id);


    @Select("SELECT * FROM user WHERE id = #{id}")
    User getUserByUsernameAndPassword(@Param("username") String username,
                                      @Param("password") String password);

    //没加avatar
    @Select("INSERT INTO user(username , encrypted_password , created_at , updated_at) " +
            "value(#{username} , #{encryptedPassword} , now() , now() )")
    void save(@Param("username") String username,
              @Param("encryptedPassword") String encryptedPassword);

    @Select("")
    String getEncryptedPasswordByUsername(String username);

    @Select("SELECT * from user where username = #{username}")
    User getUserByUsername(@Param("username") String username);


}
