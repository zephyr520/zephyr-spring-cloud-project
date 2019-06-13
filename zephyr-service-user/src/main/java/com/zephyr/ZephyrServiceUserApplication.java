package com.zephyr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.zephyr.user.dao")
@ServletComponentScan
@EnableTransactionManagement(proxyTargetClass = true)
public class ZephyrServiceUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZephyrServiceUserApplication.class, args);
	}
}
