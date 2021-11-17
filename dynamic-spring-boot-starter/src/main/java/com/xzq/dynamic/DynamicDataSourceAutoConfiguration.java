package com.xzq.dynamic;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.xzq.dynamic.aop.DsAspect;
import com.xzq.dynamic.aop.advisor.DsAdvice;
import com.xzq.dynamic.aop.advisor.DynamicDataSourceAdvisor;
import com.xzq.dynamic.aop.advisor.TransactionalAdvisor;
import com.xzq.dynamic.aop.advisor.TxAdvice;
import com.xzq.dynamic.core.DynamicRoutingDataSource;
import com.xzq.dynamic.creator.DefaultDataSourceCreator;
import com.xzq.dynamic.logo.DynamicLogo;
import com.xzq.dynamic.spring.DynamicProperties;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xzq
 * @Description //TODO
 * @Date 2021/11/11 17:35
 * @Version 1.0.0
 **/
@EnableConfigurationProperties(DynamicProperties.class)
@AutoConfigureBefore(value = DruidDataSourceAutoConfigure.class,name = "com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure")
@Import(DynamicDataSourceCreatorAutoConfiguration.class)
public class DynamicDataSourceAutoConfiguration implements InitializingBean {

    private final DynamicProperties properties;
    private final DefaultDataSourceCreator creator;
    private Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();
    public DynamicDataSourceAutoConfiguration(DynamicProperties properties,DefaultDataSourceCreator creator) {
        this.properties = properties;
        this.creator = creator;
    }

    @Bean
    public Advisor advisor() {
        DsAdvice dsAdvice = new DsAdvice();
        DynamicDataSourceAdvisor advisor = new DynamicDataSourceAdvisor(dsAdvice);
        return advisor;
    }

    @Bean
    public TransactionalAdvisor transactionalAdvisor() {
        TxAdvice txAdvice = new TxAdvice();
       return new TransactionalAdvisor(txAdvice);
    }

    @Bean
    public DynamicLogo logo() {
        return new DynamicLogo();
    }

//    @Bean
//    public DsAspect dsAspect() {
//        return new DsAspect();
//    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();
        routingDataSource.setPrimaryKey(properties.getPrimary());
        routingDataSource.setDataSourceMap(dataSourceMap);
        return routingDataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        properties.getDatasource().forEach((k,v)->{
            DataSource dataSource = creator.createDataSource(v);
            dataSourceMap.put(k, dataSource);
        });
    }
}
