package com.xzq.controller;

import com.xzq.dynamic.annotation.Ds;
import com.xzq.service.IBsCityService;
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
public class BsCityController {

    @Autowired
    private IBsCityService bsCityService;

    @RequestMapping("/test")
    public Object test(String id) {
      return   bsCityService.getById(id);
    }
}
