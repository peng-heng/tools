
package com.hengpeng.rpc.client;

import com.hengpeng.rpc.transport.Transport;

/**
 * @author hengpeng
 * Date: 2019/9/27
 */
public interface ServiceStub {
    void setTransport(Transport transport);
}
