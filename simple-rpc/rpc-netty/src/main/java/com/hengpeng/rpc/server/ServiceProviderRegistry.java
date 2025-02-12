
package com.hengpeng.rpc.server;

/**
 * @author hengpeng
 * Date: 2019/9/29
 */
public interface ServiceProviderRegistry {
    <T> void addServiceProvider(Class<? extends T> serviceClass, T serviceProvider);
}
