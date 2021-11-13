package com.xzq.service.impl;

import com.xzq.dynamic.annotation.Ds;
import com.xzq.entity.BsCity;
import com.xzq.entity.User;
import com.xzq.mapper.UserMapper;
import com.xzq.service.IBsCityService;
import com.xzq.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzq
 * @since 2021-11-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IBsCityService cityService;

    @Override
    @Transactional
    public Object queryTest(String id) {
        User user = this.getById(id);
        user.setUserNickName("熊志强");
        this.updateById(user);
        BsCity bsCity = cityService.getById(2);
        bsCity.setName("郑州");
        cityService.updateById(bsCity);
//        int i = 5 / 0;
        return true;
    }
}
