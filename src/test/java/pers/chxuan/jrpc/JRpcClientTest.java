package pers.chxuan.jrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.entity.NetworkMessage;
import pers.chxuan.jrpc.net.TcpClient;

public class JRpcClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JRpcClientTest.class);

    private static final int FIXED_LENGHT = 4 + 4 + 4 + 4;

    public static void main(String[] args) {
        TcpClient tcpClient = new TcpClient();

        if (tcpClient.connect("127.0.0.1", 9999)) {
            LOGGER.info("连接成功");

            NetworkMessage message = new NetworkMessage();
            message.setMessageClassName("RequestLogin");
            message.setContent("hello world".getBytes());
            message.setMessageClassNameLength(message.getMessageClassName().length());
            message.setContentLength(message.getContent().length);

            int totalLenght = FIXED_LENGHT
                    + message.getMessageClassNameLength()
                    + message.getContentLength();

            message.setTotalLength(totalLenght);

            tcpClient.send(message);
        } else {
            LOGGER.info("连接失败");
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
