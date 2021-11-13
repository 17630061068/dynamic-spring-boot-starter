package com.xzq.dynamic.spring;

import com.xzq.dynamic.spring.druid.DruidConfig;
import com.xzq.dynamic.spring.hikari.HikariCpConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * dynamic动态配置类
 */
@ConfigurationProperties(prefix = DynamicProperties.PREFIX)
@Data
public class DynamicProperties {

    public static final String PREFIX = "spring.datasource.dynamic";
    /**
     * 每一个数据源
     */
    private Map<String, DataSourceProperty> datasource = new LinkedHashMap<>();
    /**
     * 德鲁伊配置
     */
    private DruidConfig druid;

    /**
     * Hikari配置
     */
    private HikariCpConfig hikari;
    /**
     * 默认库
     */
    private String primary;

    /**
     *  数据源类型
     */
    private Class<? extends DataSource> type;
}
