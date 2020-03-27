package com.spc.service;

import com.spc.entity.User;
import com.spc.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements UserDetailsService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    private UserMapper userMapper;
    Map<String, String> userNameAndPassword = new ConcurrentHashMap<>();

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        save("spc","spc");

    }

//    @Inject
//    public UserService(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }

//    public User getUserById(Integer id) {
//        return userMapper.findUserById(id);
//    }


    public void save(String username, String password) {
        userNameAndPassword.put(username, bCryptPasswordEncoder.encode(password));
    }

    public String getPassword(String username) {
        return userNameAndPassword.get(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userNameAndPassword.containsKey(username)) {
            String password = userNameAndPassword.get(username);
            return new org.springframework.security.core.userdetails.User(username, password, Collections.emptyList());
        } else {
            //没对应username，用户不存在
            throw new UsernameNotFoundException(username + "  用户不存在");
        }

//        if(!userNameAndPassword.containsKey(username)){
//            throw new UsernameNotFoundException(username + "  用户不存在");
//        }
//        String password = userNameAndPassword.get(username);
//        return new org.springframework.security.core.userdetails.User(username, password, Collections.emptyList());

    }
}
