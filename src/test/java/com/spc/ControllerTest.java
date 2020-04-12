package com.spc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spc.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ControllerTest {
    private MockMvc mvc;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new Controller(authenticationManager, userService))
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8"); // 默认是ISO-8859-1
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Test
    void testNotLogin() throws Exception {
        mvc.perform(get("/auth"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("用户没登录")));
    }

    @Test
    void testLoginAndAuth() throws Exception {
        //you can use another ,such as guava
        HashMap<Object, Object> user = new HashMap<>();
        user.put("username", "spc");
        user.put("password", "spc");

        Mockito.when(userService.loadUserByUsername("spc"))
                .thenReturn(new User("spc",
                                        bCryptPasswordEncoder.encode("spc"),
                                        Collections.emptyList()));


        MvcResult mvcResult = mvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("登录成功")))
                .andReturn();

//        Arrays.toString(mvcResult.getResponse().getCookies());
    }


}