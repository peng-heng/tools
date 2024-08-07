package com.hengpeng.monitor.model;

import com.hengpeng.monitor.util.Layout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务器信息
 *
 * @author peng.heng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    /**
     * 主机名
     */
    private String hostName;

    /**
     * 操作系统名称
     */
    private String osName;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 系统架构
     */
    private String arch;

    /**
     * 本地IP
     */
    private String localIp;

    @Override
    public String toString() {
        return Layout.Table.of(
                Layout.Row.of("HostName", hostName),
                Layout.Row.of("OS", osName + "/" + osVersion),
                Layout.Row.of("Arch", arch),
                Layout.Row.of("LocalIp", localIp)
        ).toString();
    }

}