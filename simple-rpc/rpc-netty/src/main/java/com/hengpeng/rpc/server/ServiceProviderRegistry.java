
package com.hengpeng.rpc.server;

/**
 * @author LiYue
 * Date: 2019/9/29
 */
public interface ServiceProviderRegistry {
    <T> void addServiceProvider(Class<? extends T> serviceClass, T serviceProvider);
}
