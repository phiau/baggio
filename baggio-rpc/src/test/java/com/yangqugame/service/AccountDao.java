package com.yangqugame.service;

import jazmin.driver.jdbc.SmartBeanDAO;

/**
 * Created by Administrator on 2017/8/31 0031.
 */
public class AccountDao extends SmartBeanDAO<Account> {

    @Override
    public int insert(Account o, boolean withGenerateKey, String... excludeProperties) {
        return super.insert(o, withGenerateKey, excludeProperties);
    }
}
