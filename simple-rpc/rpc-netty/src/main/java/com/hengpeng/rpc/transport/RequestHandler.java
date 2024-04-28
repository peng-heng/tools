
package com.hengpeng.rpc.transport;

import com.hengpeng.rpc.transport.command.Command;

/**
 * 请求处理器
 * @author LiYue
 * Date: 2019/9/20
 */
public interface RequestHandler {
    /**
     * 处理请求
     * @param requestCommand 请求命令
     * @return 响应命令
     */
    Command handle(Command requestCommand);

    /**
     * 支持的请求类型
     */
    int type();
}
