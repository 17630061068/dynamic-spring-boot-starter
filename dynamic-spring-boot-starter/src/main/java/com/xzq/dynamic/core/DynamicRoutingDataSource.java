package com.xzq.dynamic.core;



import javax.sql.DataSource;

/**
 * @Author xzq
 * @Description //TODO DB路由实现类
 * @Date 2021/11/11 17:02
 * @Version 1.0.0
 **/
public class DynamicRoutingDataSource extends AbstractRoutingDataSource{


    @Override
    protected DataSource determineDataSource() {
        return getDataSource(DataSourceContextHolder.peek());
    }


}
