package com.xiuzhiwu.aspect;

import com.xiuzhiwu.config.DataSourceContextHolder;
import com.xiuzhiwu.enums.DataSourceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/***
 * @Author xiuzhiwu
 * @Date 2023/7/5 17:51
 * @Description
 */
@Component
@Order(-100)
@Slf4j
@Aspect
public class DataSourceAspect {

    @Pointcut("execution(* com.xiuzhiwu.mapper1..*.*(..))")
    public void clickhouseAspect(){

    }

    @Pointcut("execution(* com.xiuzhiwu.mapper2..*.*(..))")
    public void mysqlAspect(){

    }

    @Before("clickhouseAspect()")
    public void clickhouseDataSource(){
        log.info("切换数据源为{}", DataSourceTypeEnum.clickhouseDataSource.getValue());
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.clickhouseDataSource);
    }

    @Before("mysqlAspect()")
    public void mysqlDataSource(){
        log.info("切换数据源为{}", DataSourceTypeEnum.mysqlDataSource.getValue());
        DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.mysqlDataSource);
    }
}
