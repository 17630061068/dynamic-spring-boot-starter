package com.xzq.dynamic.creator;

import com.xzq.dynamic.spring.DataSourceProperty;

import javax.sql.DataSource;

/**
 * @Author xzq
 * @Description // 数据源创建器接口
 * @Date 2021/11/11 20:14
 * @Version 1.0.0
 **/
public interface DataSourceCreator {


    DataSource createrDataSource(DataSourceProperty property);

    /**
     * 当前创建其是否支持此类型创建
     * @param property
     * @return
     */
    boolean support(DataSourceProperty property);
}
