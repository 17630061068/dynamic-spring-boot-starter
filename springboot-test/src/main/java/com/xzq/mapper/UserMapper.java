package com.xzq.mapper;

import com.xzq.dynamic.annotation.Ds;
import com.xzq.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xzq
 * @since 2021-11-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    HashMap queryById(String id);
}
