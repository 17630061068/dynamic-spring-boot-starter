package com.xzq.dynamic.core;

import com.xzq.dynamic.tx.ConnectionFactory;
import com.xzq.dynamic.tx.ConnectionProxy;
import com.xzq.dynamic.tx.TransactionalContext;
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
        String xid = TransactionalContext.getXID();
        if (StringUtils.isEmpty(xid)) {
            return   determineDataSource().getConnection();
        }else{
            //进行连接代理
            String ds = DataSourceContextHolder.peek();
            ds = StringUtils.isEmpty(ds) ? "default" : ds;
            ConnectionProxy connection = ConnectionFactory.getConnection(ds);
            return connection == null ? getConnectionProxy(ds, determineDataSource().getConnection()) : connection;
        }
    }

    private Connection getConnectionProxy(String ds, Connection connection) {
        ConnectionProxy connectionProxy = new ConnectionProxy(connection, ds);
        ConnectionFactory.putConnection(ds, connectionProxy);
        return connectionProxy;
    }

    protected abstract DataSource determineDataSource();


    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        String xid = TransactionalContext.getXID();
        if (StringUtils.isEmpty(xid)) {
            return   determineDataSource().getConnection();
        }else{
            //进行连接代理
            String ds = DataSourceContextHolder.peek();
            ds = StringUtils.isEmpty(ds) ? "default" : ds;
            ConnectionProxy connection = ConnectionFactory.getConnection(ds);
            return connection == null ? getConnectionProxy(ds, determineDataSource().getConnection(username,password)) : connection;
        }
    }

    protected DataSource getDataSource(String dbKey) {
        if (!StringUtils.hasLength(dbKey)) {
            return dataSourceMap.get(primaryKey);
        }
        return dataSourceMap.get(dbKey);
    }
}
