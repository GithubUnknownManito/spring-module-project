package com.module.crud.common;

import com.module.crud.framework.interceptor.CrudResultInterceptor;
import com.module.crud.framework.interceptor.OneInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CrudConfigure {

    private boolean logicalDeletion = true;


    public boolean isLogicalDeletion() {
        return logicalDeletion;
    }

    public void setLogicalDeletion(boolean logicalDeletion) {
        this.logicalDeletion = logicalDeletion;
    }

//    @Bean
//    public CrudResultInterceptor mybatisInterceptor() {
//        CrudResultInterceptor interceptor = new CrudResultInterceptor();
//        Properties properties = new Properties();
//        // 可以调用properties.setProperty方法来给拦截器设置一些自定义参数
//        interceptor.setProperties(properties);
//        return interceptor;
//    }

    @Bean("mybatisInterceptor")
    public OneInterceptor oneInterceptor() {
        OneInterceptor interceptor = new OneInterceptor();
        Properties properties = new Properties();
        // 可以调用properties.setProperty方法来给拦截器设置一些自定义参数
        interceptor.setProperties(properties);
        return interceptor;
    }
}
