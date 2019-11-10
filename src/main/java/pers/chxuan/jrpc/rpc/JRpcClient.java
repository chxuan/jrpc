package pers.chxuan.jrpc.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.entity.NetworkMessage;
import pers.chxuan.jrpc.net.TcpClient;

public class JRpcClient extends TcpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JRpcClient.class);

    public boolean send(NetworkMessage message) {
        return super.send(message);
    }

    @Override
    protected void receivedNetworkMessageCallBack(NetworkMessage message) {
        LOGGER.info("收到服务端消息回复,message:{}", message.getMessageClassName());
    }
}
