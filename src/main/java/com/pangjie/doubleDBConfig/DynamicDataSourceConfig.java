package com.pangjie.doubleDBConfig;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfig {
    
    /**
     * 创建 DataSource Bean
     * */
    
    @Bean
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource oneDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource twoDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
    
    /**
     * 如果还有数据源,在这继续添加 DataSource Bean
     * */
    
    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource oneDataSource, DataSource twoDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceNames.ONE, oneDataSource);
        targetDataSources.put(DataSourceNames.TWO, twoDataSource);
        // 还有数据源,在targetDataSources中继续添加
        System.out.println("DataSources:" + targetDataSources);
        return new DynamicDataSource(oneDataSource, targetDataSources);
    }
}
