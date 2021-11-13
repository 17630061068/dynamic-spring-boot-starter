package com.xzq.dynamic.creator;

import com.xzq.dynamic.core.DbConstants;
import com.xzq.dynamic.spring.DataSourceProperty;
import com.xzq.dynamic.spring.DynamicProperties;
import com.xzq.dynamic.spring.hikari.HikariCpConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeanUtils;

import javax.sql.DataSource;

/**
 * @Author xzq
 * @Description //TODO
 * @Date 2021/11/11 20:45
 * @Version 1.0.0
 **/
public class HikariDataSourceCreator extends AbstractDataSourceCreator implements DataSourceCreator{

    private HikariCpConfig config;

    public HikariDataSourceCreator(DynamicProperties properties) {
        super(properties);
        config = properties.getHikari();
    }

    @Override
    protected DataSource doCreateDataSource(DataSourceProperty property) {
        HikariConfig config = new HikariConfig();
        BeanUtils.copyProperties(this.config, config);
        config.setUsername(property.getUsername());
        config.setPassword(property.getPassword());
        config.setJdbcUrl(property.getUrl());
        config.setDriverClassName(property.getDriverClassName());
        HikariDataSource hikariDataSource = new HikariDataSource(config);
        return hikariDataSource;
    }

    @Override
    public boolean support(DataSourceProperty property) {
        Class<? extends DataSource> type = this.properties.getType();
        return (type!=null && DbConstants.HIKARI_DATASOURCE.equals(type.getName()));
    }
}
