package com.uyunxun.mmp.usercenter.controller;

import com.uyunxun.mmp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hu on 2017/4/25.
 */
@RestController
public class UserCenterController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/",produces = "text/plain;charset=UTF-8")
    public String index(){
        return "Welcome Spring Boot!";
    }
}
