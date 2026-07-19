package com.amadeus.user.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * MybatisPlus配置 : 分页插件
 * @return 拦截器事例
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();

        //1.添加分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);

        //2.配置单次最大返回的数据数
        paginationInnerInterceptor.setMaxLimit(500L);

        //3.请求页码超出总页码限制,return null
        paginationInnerInterceptor.setOverflow(false);
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);

        //4.返回拦截器
        return mybatisPlusInterceptor;


    }

}
