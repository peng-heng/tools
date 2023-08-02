package com.hengpeng.main;


import com.hengpeng.spi.Join;

@Join
public class TestLogError implements  ITestLog{

    public void log() {
        System.out.println("error");
    }
}
