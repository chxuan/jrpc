package pers.chxuan.jrpc.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.entity.NetworkMessage;
import pers.chxuan.jrpc.net.TcpClient;
import pers.chxuan.jrpc.serialize.MessageSerialize;

public class JRpcClient extends TcpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JRpcClient.class);

    private static final int FIXED_LENGHT = 4 + 4 + 4 + 4;

    private MessageSerialize messageSerialize;

    public JRpcClient(MessageSerialize messageSerialize) {
        this.messageSerialize = messageSerialize;
    }

    public boolean send(Object object) {
        NetworkMessage message = makeNetworkMessage(object);
        return super.send(message);
    }

    @Override
    protected void receivedNetworkMessageCallBack(NetworkMessage message) {
        LOGGER.info("收到服务端消息回复,message:{}", message.getName());
    }

    private NetworkMessage makeNetworkMessage(Object object) {
        NetworkMessage message = new NetworkMessage();

        String name = object.getClass().getName();
        byte[] content = messageSerialize.serialize(object);

        message.setNameLength(name.length());
        message.setName(name);

        message.setContentLength(content.length);
        message.setContent(content);

        int totalLenght = FIXED_LENGHT + name.length() + content.length;
        message.setTotalLength(totalLenght);

        return message;
    }
}
