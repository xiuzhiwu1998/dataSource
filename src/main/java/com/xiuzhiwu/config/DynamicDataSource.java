package com.xiuzhiwu.config;

import com.xiuzhiwu.config.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/***
 * @Author xiuzhiwu
 * @Date 2023/7/6 16:05
 * @Description
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = DataSourceContextHolder.getCurrentDataSource();
        log.info("当前数据源为： " + dataSource);
        return dataSource;
    }
}
