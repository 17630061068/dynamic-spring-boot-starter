package com.xzq.dynamic.creator;

import com.xzq.dynamic.spring.DataSourceProperty;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Author xzq
 * @Description //TODO
 * @Date 2021/11/11 20:56
 * @Version 1.0.0
 **/
public class DefaultDataSourceCreator {
    private List<DataSourceCreator> creators;
    public DefaultDataSourceCreator(List<DataSourceCreator> creators) {
        this.creators = creators;
    }
    public DataSource createDataSource(DataSourceProperty dataSourceProperty) {
        DataSourceCreator dataSourceCreator = null;
        for (DataSourceCreator creator : creators) {
            if (creator.support(dataSourceProperty)) {
                dataSourceCreator = creator;
                break;
            }
        }
        if (dataSourceCreator == null) {
            //使用默认德鲁伊
            dataSourceCreator = creators.get(0);
        }
        return dataSourceCreator.createrDataSource(dataSourceProperty);
    }
}
