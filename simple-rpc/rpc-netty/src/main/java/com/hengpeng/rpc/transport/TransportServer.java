
package com.hengpeng.rpc.transport;

/**
 * @author LiYue
 * Date: 2019/9/25
 */
public interface TransportServer {
    void start(RequestHandlerRegistry requestHandlerRegistry, int port) throws Exception;
    void stop();
}
