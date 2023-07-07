package com.xiuzhiwu.config;

import com.xiuzhiwu.enums.DataSourceTypeEnum;

/***
 * @Author xiuzhiwu
 * @Date 2023/7/5 18:04
 * @Description
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal();

    /**
     * 设置当前数据源
     * */
    public static void setDataSourceType(DataSourceTypeEnum dataSourceTypeEnum){
        CONTEXT_HOLDER.set(dataSourceTypeEnum.getValue());
    }

    public static String getCurrentDataSource(){
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSource(){
        CONTEXT_HOLDER.remove();
    }
}
