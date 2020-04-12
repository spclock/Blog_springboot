package com.spc.service;

import com.spc.entity.User;
import com.spc.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mockEncoder;
    @Mock
    UserMapper mockUserMapper;
    @InjectMocks
    UserService userService;



    @Test
    void testSave(){
        Mockito.when(mockEncoder.encode("spc")).thenReturn("spc");
        userService.save("spc","spc");
        Mockito.verify(mockUserMapper).save("spc","spc");
    }
    @Test
    void testGetUserByUsername(){
        Mockito.when(mockUserMapper.getUserByUsername("spc")).thenReturn(new User(1,"spc","spc"));
        final User testUser = userService.getUserByUsername("spc");
        Assertions.assertEquals(1,testUser.getId());
        Assertions.assertEquals("spc",testUser.getUsername());
        Assertions.assertEquals("spc",testUser.getEncryptedPassword());
    }

    @Test
    void testloadUserByUsername(){

//        userService.getUserByUsername()
        Assertions.assertThrows(UsernameNotFoundException.class,
                ()->userService.loadUserByUsername("spc"));
    }

    @Test
    void testloadUserByUsername2(){
        Mockito.when(mockUserMapper.getUserByUsername("spc")).thenReturn(new User(1,"spc","spc"));
        final UserDetails userDetails = userService.loadUserByUsername("spc");
        Assertions.assertEquals("spc",userDetails.getUsername());
        Assertions.assertEquals("spc",userDetails.getPassword());
    }

}