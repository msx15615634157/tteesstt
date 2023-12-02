package com.panshicloud.base.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wanglibin
 * @Description:
 * @Date: 2021/7/24 13:36
 * @Version: 1.0
 */
@Configuration
public class MybatisPlusConfig {

    @Value("${spring.datasource.platform:oracle}")
    private String platform;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        return interceptor;
    }

    private InnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor(DbType.getDbType(platform));
//        return new PaginationInnerInterceptor(DbType.MYSQL);
    }

}
