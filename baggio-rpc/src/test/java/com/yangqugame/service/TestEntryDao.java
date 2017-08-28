package com.yangqugame.service;

import jazmin.driver.jdbc.QueryTerms;
import jazmin.driver.jdbc.SmartBeanDAO;

/**
 * Created by ging on 28/08/2017.
 * baggio
 */

public class TestEntryDao extends SmartBeanDAO<TestEntry> {
    @Override
    protected int queryCount(QueryTerms qt) {
        return super.queryCount(qt);
    }

    @Override
    protected TestEntry query(QueryTerms qt, String... excludeProperties) {
        return super.query(qt, excludeProperties);
    }

    @Override
    protected int insert(TestEntry o, boolean withGenerateKey, String... excludeProperties) {
        return super.insert(o, withGenerateKey, excludeProperties);
    }
}
