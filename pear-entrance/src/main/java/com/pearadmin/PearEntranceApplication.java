package com.pearadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Spring Boot Application 启 动 类
 */
@ServletComponentScan
@MapperScan( basePackages = 
        {"com.lsh.mapper.boke","com.lsh.mapper.birthday,com.pearadmin.system.mapper,com.pearadmin.schedule.mapper"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, org.activiti.spring.boot.SecurityAutoConfiguration.class, SecurityAutoConfiguration.class})
// @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PearEntranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PearEntranceApplication.class, args);
    }
}
