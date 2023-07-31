package com.hengpeng.monitor;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hengpeng.monitor.model.*;
import com.hengpeng.monitor.util.Layout;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.management.*;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonitorResult {
    /**
     * 服务器信息
     *
     * @return {@link Server}
     */
    @SneakyThrows
    private static Server server() {
        InetAddress inetAddress = InetAddress.getLocalHost();
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        return Server.builder()
                .hostName(inetAddress.getHostName())
                .osName(operatingSystemMXBean.getName())
                .osVersion(operatingSystemMXBean.getVersion())
                .arch(operatingSystemMXBean.getArch())
                .localIp(inetAddress.getHostAddress())
                .build();
    }

    /**
     * Java虚拟机信息
     *
     * @return {@link Jvm}
     */
    private static Jvm jvm() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        Map<String, String> systemProperties = runtimeMXBean.getSystemProperties();
        return Jvm.builder()
                .vmName(runtimeMXBean.getVmName())
                .jdkVersion(systemProperties.get("java.runtime.version"))
                .javaHome(systemProperties.get("java.home"))
                .pid(systemProperties.get("PID"))
                .startTime(LocalDateTimeUtil.of(runtimeMXBean.getStartTime()))
                .runtime(runtimeMXBean.getUptime())
                .startParameters(runtimeMXBean.getInputArguments())
                .build();
    }

    /**
     * CPU
     *
     * @return {@link Cpu}
     */
    private static Cpu cpu() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        JSONObject operatingSystemJson = JSON.parseObject(JSON.toJSONString(operatingSystemMXBean));
        return Cpu.builder()
                .availableProcessors(operatingSystemMXBean.getAvailableProcessors())
                .systemCpuUsage(operatingSystemJson.getBigDecimal("systemCpuLoad"))
                .processCpuUsage(operatingSystemJson.getBigDecimal("processCpuLoad"))
                .build();
    }

    /**
     * 内存
     *
     * @return {@link Memory}
     */
    private static Memory memory() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        JSONObject operatingSystemJson = JSON.parseObject(JSON.toJSONString(operatingSystemMXBean));
        long totalPhysicalMemory = operatingSystemJson.getLongValue("totalPhysicalMemorySize");
        long freePhysicalMemory = operatingSystemJson.getLongValue("freePhysicalMemorySize");
        long usedPhysicalMemory = totalPhysicalMemory - freePhysicalMemory;
        Runtime runtime = Runtime.getRuntime();
        return Memory.builder()
                .totalPhysicalMemory(totalPhysicalMemory)
                .freePhysicalMemory(freePhysicalMemory)
                .usedPhysicalMemory(usedPhysicalMemory)
                .physicalMemoryUsage(NumberUtil.toBigDecimal(NumberUtil.div(usedPhysicalMemory, totalPhysicalMemory)))
                .totalMemory(runtime.totalMemory())
                .freeMemory(runtime.freeMemory())
                .usedMemory(runtime.totalMemory() - runtime.freeMemory()).maxMemory(runtime.maxMemory())
                .maxUseMemory(runtime.maxMemory() - runtime.totalMemory() + runtime.freeMemory())
                .memoryUsage(NumberUtil.toBigDecimal(NumberUtil.div(runtime.totalMemory() - runtime.freeMemory(), runtime.totalMemory())))
                .build();
    }

    /**
     * 磁盘信息
     *
     * @return {@link Disk}
     */
    private static Disk disk() {
        List<Disk.Drive> drives = Stream.of(File.listRoots()).map(file -> Disk.Drive.builder()
                .name(file.toString())
                .totalSpace(file.getTotalSpace())
                .freeSpace(file.getFreeSpace())
                .usedSpace(file.getTotalSpace() - file.getFreeSpace())
                .driveUsage(NumberUtil.toBigDecimal(NumberUtil.div(file.getTotalSpace() - file.getFreeSpace(), file.getTotalSpace())))
                .build()
        ).collect(Collectors.toList());
        return new Disk(drives);
    }

    /**
     * 堆/非堆
     *
     * @return {@link StringBuilder}
     */
    private static Heap heap() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        return Heap.builder()
                .heapInit(heapMemoryUsage.getInit())
                .heapMaxMemory(heapMemoryUsage.getMax())
                .heapUsedMemory(heapMemoryUsage.getUsed())
                .heapFreeMemory(heapMemoryUsage.getMax() - heapMemoryUsage.getUsed())
                .nonHeapInit(nonHeapMemoryUsage.getInit())
                .nonHeapMaxMemory(nonHeapMemoryUsage.getMax())
                .nonHeapUsedMemory(nonHeapMemoryUsage.getUsed())
                .nonHeapFreeMemory(nonHeapMemoryUsage.getMax() - nonHeapMemoryUsage.getUsed())
                .build();
    }

    /**
     * 线程信息
     *
     * @return {@link MThread}
     */
    private static MThread thread() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        return MThread.builder()
                .threadCount(threadMXBean.getThreadCount())
                .peakThreadCount(threadMXBean.getPeakThreadCount())
                .daemonThreadCount(threadMXBean.getDaemonThreadCount())
                .nonDaemonThreadCount(threadMXBean.getThreadCount() - threadMXBean.getDaemonThreadCount())
                .totalStartedThreadCount(threadMXBean.getTotalStartedThreadCount())
                .build();
    }

    /**
     * 垃圾回收信息
     *
     * @return {@link Gc}
     */
    private static Gc gc() {
        long collectionCount = 0, collectionTime = 0;
        for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            collectionCount += garbageCollectorMXBean.getCollectionCount();
            collectionTime += garbageCollectorMXBean.getCollectionTime();
        }
        return Gc.builder().collectionCount(collectionCount).collectionTime(collectionTime).build();
    }

    public static void main(String[] args) {
        String msg = Stream.of(
                Layout.of("Server Info", server()),
                Layout.of("JVM", jvm()),
                Layout.of("CPU", cpu()),
                Layout.of("Memory", memory()),
                Layout.of("Disk", disk()),
                Layout.of("Heap/NonHeap", heap()),
                Layout.of("Thread", thread()),
                Layout.of("GC", gc())
        ).map(Layout::toString).collect(Collectors.joining(StrUtil.LF));
        System.out.println(msg);
    }
}
