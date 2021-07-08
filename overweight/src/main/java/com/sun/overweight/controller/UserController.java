package com.sun.overweight.controller;

import com.sun.overweight.ramp.common.model.Users;
import com.sun.overweight.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author sunwx33102
 * @description
 * @date 2021-05-14 14:48
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource(name = "userService")
    UserService userService;

    @GetMapping("getUser1")
    @ResponseBody
    @ApiOperation(value = "用户", notes = "用户")
    public Users getUser1(@RequestParam("name") String name) {
        Users users = userService.findDs1User(name);
        return users;
    }

    @GetMapping("getUser2")
    @ResponseBody
    @ApiOperation(value = "用户", notes = "用户")
    public Users getUser2(@RequestParam("name") String name){
        Users users = userService.findDs2User(name);
        return users;

    }

}