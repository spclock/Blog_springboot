package com.spc.service;

import com.spc.entity.User;
import com.spc.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserMapper userMapper;

    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder,UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper=userMapper;
    }




    public void save(String username, String password) {
        userMapper.save(username,bCryptPasswordEncoder.encode(password));
    }

    public String encryptedPassword(String username) {
        return userMapper.getEncryptedPasswordByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        if (user!=null) {
            return new org.springframework.security.core.userdetails.User(username, user.getEncryptedPassword(), Collections.emptyList());
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

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
}
