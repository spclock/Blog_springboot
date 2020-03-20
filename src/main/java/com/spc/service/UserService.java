package com.spc.service;

import com.spc.entity.User;
import com.spc.mapper.UserMapper;

import javax.inject.Inject;

public class UserService {
    private UserMapper userMapper;
    @Inject
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUserById(Integer id){
        return userMapper.findUserById(id);
    }
}
