package com.spc.Configration;

import com.spc.mapper.UserMapper;
import com.spc.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfiguration {
    @Bean
    public UserService userService(UserMapper userMapper) {
        return new UserService(userMapper);
    }
}
