package com.yangqugame.service;

import jazmin.core.Jazmin;
import jazmin.driver.jdbc.*;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ging on 28/08/2017.
 * baggio
 */


public class JDBCTest {


    private C3p0ConnectionDriver driver;


    @Before
    public void setup() throws Exception {
        if (Jazmin.getDriver(JdbcConnectionDriver.class) == null) {
            driver = new C3p0ConnectionDriver();
            driver.setUrl("jdbc:mysql://127.0.0.1:3306/baggio?user=baggio&password=baggio&useUnicode=true&characterEncoding=UTF8");
            driver.setUser("baggio");
            driver.setPassword("baggio");
            driver.setDriverClass("com.mysql.jdbc.Driver");
            driver.setInitialPoolSize(5);
            driver.setMaxPoolSize(10);
            driver.setMinPoolSize(5);
            driver.setStatSql(true);
            driver.start();
        }
        else {
            driver = Jazmin.getDriver(JdbcConnectionDriver.class);
        }

    }

    @Test
    public void testConnect() throws SQLException {
        Connection c = driver.getWorkConnection();
        MatcherAssert.assertThat(c, CoreMatchers.notNullValue());
    }

    @Test
    public void testSql() throws Exception {
        driver.startTransaction(false);
        driver.executeQuery("select * from test;", (meta, set) -> {
            try {
                while (set.next()) {
                    int id = set.getInt("id");
                    MatcherAssert.assertThat(id, CoreMatchers.equalTo(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testInsert() throws Exception {
        driver.startTransaction(true);
        boolean result = driver.getWorkConnection().prepareStatement("insert into test(id) values(2);").execute();
        driver.commit();
        MatcherAssert.assertThat(result, CoreMatchers.equalTo(false));
    }

    @Test
    public void testDaoCount() {
        TestEntryDao dao = new TestEntryDao();
        driver.startTransaction(false);
        dao.setConnectionDriver(driver);
        dao.setTableNamePrefix("");
        dao.setTableName("test");
        int count = dao.queryCount(QueryTerms.create());
        MatcherAssert.assertThat(count, CoreMatchers.equalTo(3));
    }

    @Test
    public void testDaoSelect() {
        TestEntryDao dao = new TestEntryDao();
        driver.startTransaction(false);
        dao.setConnectionDriver(driver);
        dao.setTableName("test");
        dao.setTableNamePrefix("");
        TestEntry entry = dao.query(QueryTerms.create().where("id", 1));
        MatcherAssert.assertThat(entry.id, CoreMatchers.equalTo(1));
    }

    @Test
    public void testDaoInsert() {
        TestEntryDao dao = new TestEntryDao();
        driver.startTransaction(true);
        dao.setConnectionDriver(driver);
        dao.setTableName("test");
        dao.setTableNamePrefix("");
        TestEntry entry = new TestEntry(1111);
        int rs = dao.insert(entry, false);
        driver.commit();
        MatcherAssert.assertThat(rs, CoreMatchers.equalTo(0));
    }


}
