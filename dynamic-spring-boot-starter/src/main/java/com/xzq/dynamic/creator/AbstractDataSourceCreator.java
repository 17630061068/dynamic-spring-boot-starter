package com.xzq.dynamic.creator;

import com.xzq.dynamic.spring.DataSourceProperty;
import com.xzq.dynamic.spring.DynamicProperties;

import javax.sql.DataSource;

public abstract class AbstractDataSourceCreator {

    protected final DynamicProperties properties;

    public AbstractDataSourceCreator(DynamicProperties properties) {
        this.properties = properties;
    }

    public DataSource createrDataSource(DataSourceProperty property) {
        return doCreateDataSource(property);
    }

    protected abstract DataSource doCreateDataSource(DataSourceProperty property);


}
