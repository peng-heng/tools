package com.log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4jTest {
    @Test
    public void test01() {
        BasicConfigurator.configure(); //加载初始化配置,此时没有配置文件
        Logger logger = Logger.getLogger(Log4jTest.class);
        logger.trace("trace信息");
        logger.debug("debug信息"); //默认级别
        logger.info("info信息");
        logger.warn("warn信息");
        logger.error("error信息");
        logger.fatal("fatal信息");
    }

    @Test
    public void test02() {
       org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(Log4jTest.class);
        System.out.println(logger.getLevel().name());
        logger.trace("trace信息");
        logger.debug("debug信息");
        logger.info("info信息");
        logger.warn("warn信息");
        logger.error("error信息");//默认级别
        logger.fatal("fatal信息");
    }
}