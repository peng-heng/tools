package com.hengpeng;

public class GcTest {
    // 重写 Test 类中的 finalize() 方法
    @Override
    public void finalize() throws Throwable {
        // 打印一句话作为标记，证明该方法被调用过
        System.out.println("finalize方法执行");
    }

    public static void main(String[] args) throws Exception {
        // 创建类对象 t
        GcTest t = new GcTest();
        System.out.println(t + "第一次获取对象");
        // 开启垃圾回收GC
        System.gc();
        Thread.sleep(2000);
        System.out.println(t + "第二次获取对象");
        // 显示指定为null，让t对象没有引用指向它
        t = null;
        System.gc();
        Thread.sleep(2000);
        System.out.println(t + "第三次获取对象");

    }
}