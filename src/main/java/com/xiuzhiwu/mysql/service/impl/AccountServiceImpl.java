package com.xiuzhiwu.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiuzhiwu.mapper2.AccountMapper;
import com.xiuzhiwu.mysql.model.Account;
import com.xiuzhiwu.mysql.service.AccountService;
import org.springframework.stereotype.Service;

/***
 * @Author xiuzhiwu
 * @Date 2023/7/5 16:24
 * @Description
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
        implements AccountService {

}
