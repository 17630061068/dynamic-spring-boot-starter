package com.xzq.dynamic.spring.druid;

import lombok.Data;

import java.util.Properties;

/**
 * Druid参数配置
 */
@Data
public class DruidConfig {
    private Integer initialSize;
    private Integer maxActive;
    private Integer minIdle;
    private Integer maxWait;
    private Long minEvictableIdleTimeMillis;
    private Long maxEvictableIdleTimeMillis;



    String INITIAL_SIZE = "druid.initialSize";
    String MAX_ACTIVE = "druid.maxActive";
    String MIN_IDLE = "druid.minIdle";
    String MAX_WAIT = "druid.maxWait";
    String MIN_EVICTABLE_IDLE_TIME_MILLIS = "druid.minEvictableIdleTimeMillis";
    String MAX_EVICTABLE_IDLE_TIME_MILLIS = "druid.maxEvictableIdleTimeMillis";
    public Properties toPropertes() {
        Properties properties = new Properties();
        properties.setProperty(INITIAL_SIZE, String.valueOf(initialSize));
        properties.setProperty(MAX_ACTIVE, String.valueOf(maxActive));
        properties.setProperty(MIN_IDLE, String.valueOf(minIdle));
        properties.setProperty(MAX_WAIT, String.valueOf(maxWait));
        properties.setProperty(MIN_EVICTABLE_IDLE_TIME_MILLIS, String.valueOf(minEvictableIdleTimeMillis));
        properties.setProperty(MAX_EVICTABLE_IDLE_TIME_MILLIS, String.valueOf(maxEvictableIdleTimeMillis));
        return properties;
    }
}