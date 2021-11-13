package com.xzq.dynamic.core;

/**
 * @Author xzq
 * @Description //Dynamic数据源常量
 * @Date 2021/11/11 20:34
 * @Version 1.0.0
 **/
public interface DbConstants {
    /**
     * DRUID数据源类
     */
    String DRUID_DATASOURCE = "com.alibaba.druid.pool.DruidDataSource";

    /**
     * HikariCp数据源
     */
    String HIKARI_DATASOURCE = "com.zaxxer.hikari.HikariDataSource";
}
