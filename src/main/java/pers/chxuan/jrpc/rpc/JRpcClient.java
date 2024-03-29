package pers.chxuan.jrpc.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.entity.NetworkMessage;
import pers.chxuan.jrpc.tcp.TcpClient;
import pers.chxuan.jrpc.tcp.TcpConnectStatus;
import pers.chxuan.jrpc.serialize.MessageSerialize;
import pers.chxuan.jrpc.util.NetworkMessageUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JRpcClient extends TcpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JRpcClient.class);

    private MessageSerialize messageSerialize;

    private Map<Integer, JRpcFuture> futureMap = new ConcurrentHashMap<>();

    private static final long DEFAULT_SEND_TIMEOUT = 60 * 1000;

    public JRpcClient(String ip, int port, MessageSerialize messageSerialize) {
        super(ip, port);
        this.messageSerialize = messageSerialize;
    }

    public Object send(Object object) {
        return send(object, DEFAULT_SEND_TIMEOUT);
    }

    public synchronized Object send(Object object, long timeout) {
        int connectStatus = super.connectStatus.get();

        if (connectStatus == TcpConnectStatus.CONNECTED) {
            return doSend(object, timeout);
        } else if (connectStatus == TcpConnectStatus.NOT_CONNECT){
            if (super.connect()) {
                return doSend(object, timeout);
            }
        }

        return null;
    }

    @Override
    protected void receivedNetworkMessageCallBack(NetworkMessage message) {
        JRpcFuture future = futureMap.get(message.getSerial());
        if (future != null) {
            future.setObject(NetworkMessageUtils.toUserObject(messageSerialize, message));
            future.wakeup();
        } else {
            LOGGER.info("收到服务端消息回复,没有找到JRpcFuture,name:{}", message.getName());
        }
    }

    private Object doSend(Object object, long timeout) {
        NetworkMessage message = NetworkMessageUtils.toNetworkMessage(messageSerialize, object);
        if (message != null) {
            JRpcFuture future = new JRpcFuture();
            futureMap.put(message.getSerial(), future);

            if (super.send(message)) {
                future.await(timeout);
            }

            futureMap.remove(message.getSerial());
            return future.getObject();
        }

        return null;
    }
}
