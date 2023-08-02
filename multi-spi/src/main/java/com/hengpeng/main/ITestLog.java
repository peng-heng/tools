package com.hengpeng.main;


import com.hengpeng.spi.SPI;

@SPI
public interface ITestLog {

    default void log() {
    }
}
