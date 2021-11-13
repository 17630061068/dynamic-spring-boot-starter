package com.xzq.dynamic.spring;

import lombok.Data;

import java.util.Properties;

@Data
public class DataSourceProperty {
    /**
     * JDBC driver
     */
    private String driverClassName;
    /**
     * JDBC url 地址
     */
    private String url;
    /**
     * JDBC 用户名
     */
    private String username;
    /**
     * JDBC 密码
     */
    private String password;

}
