package com.xzq.dynamic;

import com.xzq.dynamic.creator.DataSourceCreator;
import com.xzq.dynamic.creator.DefaultDataSourceCreator;
import com.xzq.dynamic.creator.DruidDataSourceCreator;
import com.xzq.dynamic.creator.HikariDataSourceCreator;
import com.xzq.dynamic.spring.DynamicProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author xzq
 * @Description //TODO
 * @Date 2021/11/11 21:02
 * @Version 1.0.0
 **/
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(DynamicProperties.class)
public class DynamicDataSourceCreatorAutoConfiguration {

    private final DynamicProperties properties;


    @Bean
    @ConditionalOnMissingBean
    public DefaultDataSourceCreator defaultDataSourceCreator(List<DataSourceCreator> creatorList) {
       return new DefaultDataSourceCreator(creatorList);
    }

    @Bean
    @ConditionalOnMissingBean
    public DruidDataSourceCreator druidDataSourceCreator() {
        return new DruidDataSourceCreator(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public HikariDataSourceCreator hikariDataSourceCreator() {
        return new HikariDataSourceCreator(properties);
    }

}
