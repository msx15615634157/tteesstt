package com.panshicloud.base;

import com.panshicloud.base.operationlog.EnableOperationLog;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @Author: wanglibin
 * @Description: Application start
 * @Date: 2021/6/29 15:47
 * @Version: 1.0
 */
@MapperScan({"com.panshicloud.base.dao.mapper"})
@SpringBootApplication
@EnableOperationLog
@ComponentScan({"com.panshicloud.common.lock", "com.panshicloud.base","com.panshicloud.common.controller"})
@ServletComponentScan(basePackages = "com.panshicloud.common.filter")
@EnableAsync
@EnableScheduling
@EnableCaching
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
