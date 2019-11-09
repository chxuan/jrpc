package pers.chxuan.jrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.net.TcpClient;

public class JRpcClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JRpcClientTest.class);

    public static void main(String[] args) {
        TcpClient tcpClient = new TcpClient();

        if (tcpClient.connect("127.0.0.1", 9999)) {
            LOGGER.info("连接成功");
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
