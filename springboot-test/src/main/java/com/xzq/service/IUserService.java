package com.xzq.service;

import com.xzq.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xzq
 * @since 2021-11-11
 */
public interface IUserService extends IService<User> {

    Object queryTest(String id);
}
