package com.xiuzhiwu.clickHouse.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiuzhiwu.mapper1.TestOrderMapper;
import com.xiuzhiwu.clickHouse.model.TestOrder;
import org.springframework.stereotype.Service;

/***
 * @Author xiuzhiwu
 * @Date 2023/7/5 9:51
 * @Description
 */
@Service
public class TestOrderServiceImpl
        extends ServiceImpl<TestOrderMapper, TestOrder> implements TestOrderService{


}
