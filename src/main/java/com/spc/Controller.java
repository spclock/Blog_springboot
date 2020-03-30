package com.spc;

import com.spc.entity.User;
import com.spc.service.OrderService;
import com.spc.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {
    //    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Inject
    public Controller(AuthenticationManager authenticationManager,
                      UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Resource
    private OrderService orderService;

//    @Inject
//    public Controller(OrderService orderService, UserService userService) {
//        this.orderService = orderService;
//        this.userService = userService;
//    }

    @GetMapping("/auth")
    public Object authRegister() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!name.contains("anonymousUser")) {
            return new Result("ok", "cookie验证了", true, userService.getUserByUsername(name));
        } else {
            return new Result("ok", "用户没登录", false);
        }

    }


//    docker run --name blog-mysql -e MYSQL_ROOT_PASSWORD=root
//    -e MYSQL_DATABASE=blog -p 3306:3306 -d mysql:5.7.29

    @PostMapping("/auth/login")
    public Result authLogin(@RequestBody Map<String, Object> user) {
        String username = user.get("username").toString();
        String password = user.get("password").toString();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return new Result("fail", "用户不存在", false);
        }
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);

//            User loginInUser = new User(1, "张三");

            return new Result("ok", "登录成功", true, userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            return new Result("fail", "登录失败", false);
        }
    }
    @PostMapping("/auth/register")
    public Result register(@RequestBody Map<String, Object> user){
        String username = user.get("username").toString();
        String password = user.get("password").toString();
        try{
            userService.save(username,password);
            User registeredUser = userService.getUserByUsername(username);
            return new Result("ok","注册成功",true,registeredUser);

        }catch (Exception e) {
            return new Result("false","错误原因",false);
        }
    }

    @RequestMapping("/")
    public User index() {
//        return "Greetings from Spring Boot!"+name;
        return userService.getUserByUsername("spc");
    }

    private class Result {
        String status;
        String msg;
        boolean isLogin;
        User data;

        public Result(String status, String msg, boolean isLogin) {
            this(status, msg, isLogin, null);
        }

        public Result(String status, String msg, User user) {
            this.status = status;
            this.msg = msg;
            this.data = user;
        }

        public Result(String status, String msg, boolean isLogin, User user) {
            this.status = status;
            this.msg = msg;
            this.isLogin = isLogin;
            this.data = user;
        }

        public String getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

        public boolean isLogin() {
            return isLogin;
        }

        public User getData() {
            return data;
        }
    }
}
