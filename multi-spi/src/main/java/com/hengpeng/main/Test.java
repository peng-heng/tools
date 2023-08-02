package com.hengpeng.main;

import com.hengpeng.spi.ExtensionLoader;

public class Test {
    public static void main(String[] args) {
        ExtensionLoader.getExtensionLoader(ITestLog.class).getJoin("info").log();
        ExtensionLoader.getExtensionLoader(ITestLog.class).getJoin("error").log();
    }
}


