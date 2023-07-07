package com.xiuzhiwu.mysql.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/***
 * @Author xiuzhiwu
 * @Date 2023/7/5 16:19
 * @Description
 */
@Data
@ToString
@TableName("account")
public class Account {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer           id;
    private String            accountName;
    private String            accountNameCn;
    private String            parentAccountName;
    private Integer           level;
    private String            companyNo;
    private BigDecimal        balance;
    private BigDecimal        cumulativeAmount;      // 累计预存款
    private Double            balanceAlertThreshold; // 余额预警阈值
    private BigDecimal        creditLimit;
    private String            appKey;                // 应用ID
    private String            appSecret;             // 应用密码
    private Integer           status;
    private String            creater;
    private LocalDateTime     createtime;
    private String            operator;
    private LocalDateTime     updatetime;
}
