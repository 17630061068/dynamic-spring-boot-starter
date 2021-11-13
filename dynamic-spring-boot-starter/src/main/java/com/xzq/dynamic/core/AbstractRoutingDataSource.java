package com.xzq.dynamic.core;

import lombok.Data;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xzq
 * @Description //抽象路由模板类
 * @Date 2021/11/11 16:45
 * @Version 1.0.0
 **/
@Data
public abstract class AbstractRoutingDataSource extends AbstractDataSource {

    private Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();
    private String primaryKey;

    @Override
    public Connection getConnection() throws SQLException {
      return   determineDataSource().getConnection();
    }

    protected abstract DataSource determineDataSource();


    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineDataSource().getConnection(username, password);
    }

    protected DataSource getDataSource(String dbKey) {
        if (!StringUtils.hasLength(dbKey)) {
            return dataSourceMap.get(primaryKey);
        }
        return dataSourceMap.get(dbKey);
    }
}
