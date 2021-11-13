package com.xzq.dynamic.creator;

import com.alibaba.druid.pool.DruidDataSource;
import com.xzq.dynamic.core.DbConstants;
import com.xzq.dynamic.spring.DataSourceProperty;
import com.xzq.dynamic.spring.DynamicProperties;
import com.xzq.dynamic.spring.druid.DruidConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

import static com.xzq.dynamic.core.DbConstants.DRUID_DATASOURCE;

/**
 * @Author xzq
 * @Description //TODO
 * @Date 2021/11/11 20:19
 * @Version 1.0.0
 **/
public class DruidDataSourceCreator extends AbstractDataSourceCreator implements DataSourceCreator{

    private Logger logger = LoggerFactory.getLogger(DruidDataSourceCreator.class);

    private final DruidConfig config;

    public DruidDataSourceCreator(DynamicProperties properties) {
        super(properties);
        config = properties.getDruid();
    }

    @Override
    protected DataSource doCreateDataSource(DataSourceProperty property) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(property.getDriverClassName());
        druidDataSource.setUsername(property.getUsername());
        druidDataSource.setPassword(property.getPassword());
        druidDataSource.setUrl(property.getUrl());
        Properties properties = config.toPropertes();
        druidDataSource.configFromPropety(properties);
        try {
            druidDataSource.init();
        } catch (SQLException e) {
            logger.error("druid init fail");
            new RuntimeException("druid init fail", e);
        }
        return druidDataSource;
    }
    @Override
    public boolean support(DataSourceProperty property) {
        Class<? extends DataSource> type = properties.getType();
        return (type != null && DRUID_DATASOURCE.equals(type.getName()));
    }

}
