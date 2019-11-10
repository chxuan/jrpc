package pers.chxuan.jrpc.rpc;

import pers.chxuan.jrpc.serialize.MessageSerialize;

import java.util.concurrent.TimeUnit;

public class JRpcClientPool {

    private String ip;

    private int port;

    private MessageSerialize messageSerialize;

    private ThreadLocal<JRpcClient> rpcPool = ThreadLocal.withInitial(() -> {
        try {
            return new JRpcClient(ip, port, messageSerialize);
        } catch (Exception e) {
            return null;
        }
    });

    public JRpcClientPool(String ip, int port, MessageSerialize messageSerialize) {
        this.ip = ip;
        this.port = port;
        this.messageSerialize = messageSerialize;
    }

    public Object send(Object object) {
        return rpcPool.get().send(object);
    }

    public Object send(Object object, long timeout, TimeUnit unit) {
        return rpcPool.get().send(object, timeout, unit);
    }
}
