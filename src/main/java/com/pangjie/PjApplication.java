package com.pangjie;

import cn.dev33.satoken.spring.SaTokenSetup;
import com.pangjie.doubleDBConfig.DynamicDataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SaTokenSetup // 必须有这个注解，用来标注加载sa-token
@Import({DynamicDataSourceConfig.class})//多数据源
@EnableTransactionManagement
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})//多数据源
public class PjApplication {

	public static void main(String[] args) {
		SpringApplication.run(PjApplication.class, args);
	}

}
