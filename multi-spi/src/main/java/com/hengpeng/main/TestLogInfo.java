package com.hengpeng.main;


import com.hengpeng.spi.Join;

@Join
public class TestLogInfo implements  ITestLog{

    public void log() {
        System.out.println("info");
    }
}
