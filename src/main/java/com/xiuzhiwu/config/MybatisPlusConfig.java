package com.xiuzhiwu.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.xiuzhiwu.enums.DataSourceTypeEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/***
 * @Author xiuzhiwu
 * @Date 2023/7/6 16:13
 * @Description
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.xiuzhiwu.mapper2")
@MapperScan("com.xiuzhiwu.mapper1")
public class MybatisPlusConfig {

    /**
     * 分页插件
     * */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.CLICK_HOUSE));

        return interceptor;
    }

    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "generator.jdbc")
    public DataSource mysqlDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "clickhouseDataSource")
    @ConfigurationProperties(prefix = "clickhouse.jdbc.datasource")
    public DataSource clickDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("mysqlDataSource") DataSource mysqlDataSource,
                                         @Qualifier("clickhouseDataSource") DataSource clickhouseDataSource){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 放置多数据源
        Map<Object,Object> targetDataSourceMap = new HashMap<>();
        targetDataSourceMap.put(DataSourceTypeEnum.clickhouseDataSource.getValue(), clickhouseDataSource);
        targetDataSourceMap.put(DataSourceTypeEnum.mysqlDataSource.getValue(), mysqlDataSource);
        dynamicDataSource.setTargetDataSources(targetDataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(mysqlDataSource);

        return dynamicDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory() throws Exception{
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        // 向SqlSessionFactory注入数据源
        factoryBean.setDataSource(this.multipleDataSource(mysqlDataSource(),clickDataSource()));
        // 设置mapper的xml路径
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml"));

        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);
        mybatisConfiguration.setMapUnderscoreToCamelCase(false);
        mybatisConfiguration.setCacheEnabled(false);

        factoryBean.setConfiguration(mybatisConfiguration);
        factoryBean.setPlugins(mybatisPlusInterceptor());

        return factoryBean.getObject();
    }

}





























