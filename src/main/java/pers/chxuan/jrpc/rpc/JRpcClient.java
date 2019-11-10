package pers.chxuan.jrpc.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.entity.NetworkMessage;
import pers.chxuan.jrpc.net.TcpClient;
import pers.chxuan.jrpc.serialize.MessageSerialize;
import pers.chxuan.jrpc.utils.NetworkMessageUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class JRpcClient extends TcpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JRpcClient.class);

    private MessageSerialize messageSerialize;

    private Map<Integer, JRpcFuture> futureMap = new ConcurrentHashMap<>();

    public JRpcClient(MessageSerialize messageSerialize) {
        this.messageSerialize = messageSerialize;
    }

    public synchronized Object send(Object object) {
        JRpcFuture future = new JRpcFuture();
        NetworkMessage message = NetworkMessageUtils.toNetworkMessage(messageSerialize, object);

        futureMap.put(message.getSerial(), future);

        if (super.send(message)) {
            future.await(2, TimeUnit.SECONDS);
        }

        futureMap.remove(message.getSerial());

        return future.getObject();
    }

    @Override
    protected void receivedNetworkMessageCallBack(NetworkMessage message) {
        JRpcFuture future = futureMap.get(message.getSerial());
        if (future != null) {
            future.setObject(NetworkMessageUtils.toUserObject(messageSerialize, message));
            future.notif();
        } else {
            LOGGER.info("收到服务端消息回复,没有找到JRpcFuture,name:{}", message.getName());
        }
    }
}
