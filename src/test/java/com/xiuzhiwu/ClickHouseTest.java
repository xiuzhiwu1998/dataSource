package com.xiuzhiwu;

import com.xiuzhiwu.clickHouse.model.TestOrder;
import com.xiuzhiwu.clickHouse.service.TestOrderService;
import com.xiuzhiwu.mysql.model.Account;
import com.xiuzhiwu.mysql.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/***
 * @Author xiuzhiwu
 * @Date 2023/7/5 9:55
 * @Description
 */
@SpringBootTest
public class ClickHouseTest {

    @Autowired
    private TestOrderService testOrderService;
    @Autowired
    private AccountService accountService;


    @Test
    void getOrderList(){
        List<TestOrder> testOrders = testOrderService.list();
        List<Account> accounts = accountService.list();

        testOrders.stream().forEach(System.out::println);
        System.out.println("-------------------------------");
        accounts.stream().forEach(System.out::println);
    }
}
