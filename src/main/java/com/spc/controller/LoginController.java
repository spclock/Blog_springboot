package com.spc.controller;

import com.spc.entity.LoginResult;
import com.spc.entity.Result;
import com.spc.entity.User;
import com.spc.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

@RestController
public class LoginController {
    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Inject
    public LoginController(AuthenticationManager authenticationManager,
                           UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }


    @GetMapping("/auth")
    public LoginResult auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.getUserByUsername(authentication == null ? null : authentication.getName());

        if (user != null) {
            return new LoginResult("ok", null, true, user);
        } else {
            return new LoginResult("ok", "用户没登录", false);
        }

    }

    @GetMapping("/auth/logout")
    public LoginResult logout() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        if (user != null) {
            SecurityContextHolder.clearContext();
            return new LoginResult("ok", "注销成功", false);
        } else {
            return new LoginResult("fail", "用户尚未登录", false);

        }

    }


//    docker run --name blog-mysql -e MYSQL_ROOT_PASSWORD=root
//    -e MYSQL_DATABASE=blog -p 3306:3306 -d mysql:5.7.29

    @PostMapping("/auth/login")
    public LoginResult authLogin(@RequestBody Map<String, Object> user) {
        String username = user.get("username").toString();
        String password = user.get("password").toString();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return new LoginResult("fail", "用户不存在", false);
        }

        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);

            return new LoginResult("ok", "登录成功;", true, userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            return new LoginResult("fail", "登录失败", false);
        }
    }

    //注册有个地方要补全，你register后要setContent，不然auth不出状态
    @PostMapping("/auth/register")
    public LoginResult register(@RequestBody Map<String, Object> user) {
        String username = user.get("username").toString();
        String password = user.get("password").toString();
        if (username == null || password == null) {
            return new LoginResult("fail", "username or password is null", false);
        }

        //需要换成正则表达式
        if (username.length() < 1 || username.length() > 15) {
            return new LoginResult("fail", "invalid username", false);
        }
        if (password.length() < 6 || password.length() > 16) {
            return new LoginResult("fail", "invalid password, you need create 6-16", false);
        }
        try {
            userService.save(username, password);
            User registeredUser = userService.getUserByUsername(username);
            return new LoginResult("ok", "注册成功", true);
        } catch (DuplicateKeyException e) {
            return new LoginResult("fail", "用户存在", false);
        }
    }

    @RequestMapping("/")
    public User index() {
        return userService.getUserByUsername("spc");
    }

}
