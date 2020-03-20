package com.spc;

import com.spc.entity.User;
import com.spc.service.OrderService;
import com.spc.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@RestController
public class Controller {
    private OrderService orderService;
    private UserService userService;

    @Inject
    public Controller(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }






    @RequestMapping("/")
    public User index() {
//        return "Greetings from Spring Boot!"+name;
        return userService.getUserById(1);
    }

}
