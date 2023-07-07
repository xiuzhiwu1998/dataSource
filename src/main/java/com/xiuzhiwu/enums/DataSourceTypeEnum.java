package com.xiuzhiwu.enums;

/***
 * @Author xiuzhiwu
 * @Date 2023/7/5 17:58
 * @Description
 */
public enum DataSourceTypeEnum {
    mysqlDataSource("mysqlDataSource"),
    clickhouseDataSource("clickhouseDataSource"),
    ;

    private String value;

    DataSourceTypeEnum(String targetValue) {
        this.value = targetValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
