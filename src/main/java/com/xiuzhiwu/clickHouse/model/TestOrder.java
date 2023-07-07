package com.xiuzhiwu.clickHouse.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/***
 * @Author xiuzhiwu
 * @Date 2023/7/5 9:36
 * @Description
 */
@Data
@ToString
@TableName("test_order")
public class TestOrder {
    private Long id;
    private String userName;
    private String orderNo;
    private String payTime;
}
