
package com.hengpeng.rpc.client.stubs;

import com.hengpeng.rpc.client.RequestIdSupport;
import com.hengpeng.rpc.client.ServiceStub;
import com.hengpeng.rpc.client.ServiceTypes;
import com.hengpeng.rpc.serialize.SerializeSupport;
import com.hengpeng.rpc.transport.Transport;
import com.hengpeng.rpc.transport.command.Code;
import com.hengpeng.rpc.transport.command.Command;
import com.hengpeng.rpc.transport.command.Header;
import com.hengpeng.rpc.transport.command.ResponseHeader;

import java.util.concurrent.ExecutionException;

/**
 * @author LiYue
 * Date: 2019/9/27
 */
public abstract class AbstractStub implements ServiceStub {
    protected Transport transport;

    protected byte [] invokeRemote(RpcRequest request) {
        Header header = new Header(ServiceTypes.TYPE_RPC_REQUEST, 1, RequestIdSupport.next());
        byte [] payload = SerializeSupport.serialize(request);
        Command requestCommand = new Command(header, payload);
        try {
            Command responseCommand = transport.send(requestCommand).get();
            ResponseHeader responseHeader = (ResponseHeader) responseCommand.getHeader();
            if(responseHeader.getCode() == Code.SUCCESS.getCode()) {
                return responseCommand.getPayload();
            } else {
                throw new Exception(responseHeader.getError());
            }

        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
