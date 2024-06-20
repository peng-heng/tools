package com.jul;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.*;

public class JulTest {

    @Test
    public void test01() {
        // 引入当前类的全路径字符串获取日志记录器
        Logger logger = Logger.getLogger(this.getClass().getName());

        // 对于日志的输出有两种方式
        // 1、直接调用日志级别的相关方法，方法中传递日志输出信息
        logger.info("info信息1");

        // 2、调用log方法，通过Level类型定义日志级别参数，以及搭配日志输出信息的参数
        logger.log(Level.INFO, "info信息2");

        System.out.println("--------");

        // 打印日志信息并传参
        // 输出学生信息：姓名、年龄
        String name = "张三";
        int age = 23;
        logger.log(Level.INFO, "方式一：学生姓名：" + name + "，学生年龄：" + age);

        // 以上操作中，对于输出消息用字符串拼接弊端很多。拼接麻烦、程序效率低、可读性不强、维护成本高
        // 应该使用动态生成数据的方式生产日志，就是占位符的方式来进行操作
        logger.log(Level.INFO, "方式二：学生姓名：{0}，学生年龄:{1}", new Object[]{name, age});
    }

    /**
     * 默认日志级别是 INFO
     */
    @Test
    public void test02() {
        // 引入当前类的全路径字符串获取日志记录器
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.severe("severe信息");
        logger.warning("warning信息");
        logger.info("info信息");
        logger.config("config信息");
        logger.fine("fine信息");
        logger.finer("finer信息");
        logger.finest("finest信息");
    }

    /**
     * 修改日志级别
     */
    @Test
    public void test03() {
        // 引入当前类的全路径字符串获取日志记录器
        Logger logger = Logger.getLogger(this.getClass().getName());
        // 将默认的日志打印方式关闭
        // 参数设置为 false，打印日志的方式就不会按照父 logger 默认的方式去进行操作
        logger.setUseParentHandlers(false);
        // 控制台日志处理器
        ConsoleHandler handler = new ConsoleHandler();
        // 创建日志格式化组件对象
        SimpleFormatter formatter = new SimpleFormatter();
        // 在处理器中设置日志输出格式
        handler.setFormatter(formatter);
        // 在记录器中添加处理器
        logger.addHandler(handler);
        // 设置日志的打印级别
        // 此处必须将日志记录器和处理器的级别进行统一的设置，才会达到日志显示相应级别的效果
        logger.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);
        // 输出日志信息
        logger.severe("severe：错误信息");
        logger.warning("warning：警告信息");
        logger.info("info：默认信息");
        logger.config("config：配置信息");
        logger.fine("fine：详细信息(少)");
        logger.finer("finer：详细信息(中)");
        logger.finest("finest：详细信息(多)");
    }

    /**
     * 日志打印到文件
     */
    @Test
    public void test04() throws IOException {
        // 引入当前类的全路径字符串获取日志记录器
        Logger logger = Logger.getLogger(this.getClass().getName());
        // 关闭父记录器打印方式
        logger.setUseParentHandlers(false);

        // 文件日志处理器
        FileHandler handler = new FileHandler("src\\jul.log"); // 指定输出的日志文件
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        logger.addHandler(handler);

        // 统一设置日志的打印级别
        logger.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);

        // 输出日志信息
        logger.severe("severe：错误信息");
        logger.warning("warning：警告信息");
        logger.info("info：默认信息");
        logger.config("config：配置信息");
        logger.fine("fine：详细信息(少)");
        logger.finer("finer：详细信息(中)");
        logger.finest("finest：详细信息(多)");
    }

    /**
     * 多个处理器
     * 用户使用 Logger 来进行日志的记录，使用 Handler 来进行日志的输出，Logger 可以持有多个处理器 Handler，
     * 添加了哪些 Handler 对象，就相当于根据所添加的 Handler 将日志输出到指定的位置上，例如控制台、文件中…
     */
    @Test
    public void test05() throws IOException {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.setUseParentHandlers(false);
        SimpleFormatter formatter = new SimpleFormatter();

        // 文件日志处理器
        FileHandler handler1 = new FileHandler("src\\Jul2.log"); // 指定输出的日志文件
        handler1.setFormatter(formatter);
        logger.addHandler(handler1); // 记录器中添加了一个文件日志处理器

        // 控制台日志处理器
        ConsoleHandler handler2 = new ConsoleHandler();
        handler2.setFormatter(formatter);
        logger.addHandler(handler2); // 记录器中又添加了一个控制台日志处理器

        // 统一设置日志的打印级别
        logger.setLevel(Level.ALL);
        handler1.setLevel(Level.ALL);
        handler2.setLevel(Level.ALL);

        // 输出日志信息
        logger.severe("severe：错误信息");
        logger.warning("warning：警告信息");
        logger.info("info：默认信息");
        logger.config("config：配置信息");
        logger.fine("fine：详细信息(少)");
        logger.finer("finer：详细信息(中)");
        logger.finest("finest：详细信息(多)");
    }

    /**
     * JUL 中 Logger 记录器之间是存在 “父子” 关系的，这种父子关系不是我们普遍认为的类之间的继承关系，关系是通过树状结构存储的。
     * <p>
     * JUL 在初始化时会创建一个顶层 RootLogger 作为所有 Logger 的父 Logger，RootLogger 是 LogManager 的内部类，默认的名称为空串。
     * <p>
     * 以上的 RootLogger 对象作为树状结构的根节点存在的，将来自定义的父子关系通过路径来进行关联，父子关系同时也是节点之间的挂载关系。
     */
    @Test
    public void test06() {
        // 创建两个 logger 对象，可以认为 logger1 是 logger2 的父亲
        // RootLogger 是所有 logger 对象的顶层 logger，名称默认是一个空的字符串
        Logger logger1 = Logger.getLogger("com.jul");
        Logger logger2 = Logger.getLogger("com.jul.JulTest");

        System.out.println(logger2.getParent() == logger1);
        System.out.println("----");

        System.out.println("logger1名称：" + logger1.getName() +
                "，\n父Logger名称：" + logger1.getParent().getName() +
                "，\n父Logger引用：" + logger1.getParent());
        System.out.println("----");

        System.out.println("logger2名称：" + logger2.getName() +
                "，\n父Logger名称：" + logger2.getParent().getName() +
                "，\n父Logger引用：" + logger2.getParent());
        System.out.println("----");

        // 父亲所做的设置，也能够同时作用于儿子
        // 对 logger1 做日志打印相关的设置，然后我们使用 logger2 进行日志的打印
        logger1.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        logger1.addHandler(handler);
        handler.setLevel(Level.ALL);
        logger1.setLevel(Level.ALL);

        //儿子做打印
        logger2.severe("severe：错误信息");
        logger2.warning("warning：警告信息");
        logger2.info("info：默认信息");
        logger2.config("config：配置信息");
        logger2.fine("fine：详细信息(少)");
        logger2.finer("finer：详细信息(中)");
        logger2.finest("finest：详细信息(多)");
    }
    /**
     * 以上所有配置的相关操作，都是以 java 硬编码的形式进行的，我们可以使用配置文件，若没有指定自定义日志配置文件，则使用系统默认的日志配置文件
     * 默认配置文件位置： jdk 安装目录下 \ jre \ lib \ logging.properties 文件
     */
    // 自定义配置文件
    @Test
    public void test07() throws IOException {
        // 读取自定义日志配置文件
        InputStream input = Files.newInputStream(Paths.get("src/main/resources/logging.properties"));
        // 获取日志管理器
        LogManager logManager = LogManager.getLogManager();
        // 日志管理器读取自定义配置文件
        logManager.readConfiguration(input);
        // 日志记录器
        Logger logger = Logger.getLogger("com.jul.JulTest");
        // 输出日志信息
        logger.severe("severe：错误信息");
        logger.warning("warning：警告信息");
        logger.info("info：默认信息");
        logger.config("config：配置信息");
        logger.fine("fine：详细信息(少)");
        logger.finer("finer：详细信息(中)");
        logger.finest("finest：详细信息(多)");
    }
}

