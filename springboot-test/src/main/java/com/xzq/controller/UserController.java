package com.xzq.controller;


import com.xzq.dynamic.annotation.Ds;
import com.xzq.mapper.UserMapper;
import com.xzq.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xzq
 * @since 2021-11-11
 */
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;


    @RequestMapping("/test2")
    @Ds("second")
    public Object test(String id) {
      return   userService.getById(id);
    }

}
