package com.yangqugame.global;

import com.yangqugame.utils.StringUtils;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 服务器一些静态配置
 * Created by Administrator on 2017/9/6 0006.
 */
public class ConfigManager {

    final transient static Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    public static boolean loadDbConfig(String dbFilePath) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(dbFilePath);
            prop.load(input);

            if (!StringUtils.isNullOrEmpty(prop.getProperty("address"))) {
                DbConfig.setAddress(prop.getProperty("address"));
            }
            if (!StringUtils.isNullOrEmpty(prop.getProperty("port"))) {
                DbConfig.setPort(prop.getProperty("port"));
            }
            if (!StringUtils.isNullOrEmpty(prop.getProperty("user"))) {
                DbConfig.setUser(prop.getProperty("user"));
            }
            if (!StringUtils.isNullOrEmpty(prop.getProperty("pwd"))) {
                DbConfig.setPsw(prop.getProperty("pwd"));
            }
            if (!StringUtils.isNullOrEmpty(prop.getProperty("dataDbName"))) {
                DbConfig.setDataDbName(prop.getProperty("dataDbName"));
            }
            if (!StringUtils.isNullOrEmpty(prop.getProperty("confDbName"))) {
                DbConfig.setConfDbName(prop.getProperty("confDbName"));
            }
            if (!StringUtils.isNullOrEmpty(prop.getProperty("charSet"))) {
                DbConfig.setCharSet(prop.getProperty("charSet"));
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("", e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("", e);
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("load {} error,", dbFilePath, e);
                }
            }
        }
        return false;
    }

    public static boolean loadBaseConfig(String filePath) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(filePath);
            prop.load(input);

            if (!StringUtils.isNullOrEmpty(prop.getProperty("serverId"))) {
                BaseConfig.setServerId(Short.parseShort(prop.getProperty("serverId")));
            }
            if (!StringUtils.isNullOrEmpty(prop.getProperty("status"))) {
                BaseConfig.setStatus(Byte.parseByte(prop.getProperty("status")));
            }
            if (!StringUtils.isNullOrEmpty(prop.getProperty("dbConfigFile"))) {
                BaseConfig.setDbConfigFile(prop.getProperty("dbConfigFile"));
            }
            if (!StringUtils.isNullOrEmpty(prop.getProperty("verifyServerUrl"))) {
                BaseConfig.setVerifyServerUrl(prop.getProperty("verifyServerUrl"));
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("", e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("", e);
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("load {} error,", filePath, e);
                }
            }
        }
        return false;
    }

    public static void main(String [] argv) {
        loadBaseConfig("./baggio-message/src/main/config/config.properties");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }

}
