package pers.chxuan.jrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.tcp.TcpServer;

public class JRpcServerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JRpcServerTest.class);

    public static void main(String[] args) {
        TcpServer tcpServer = new TcpServer(1);
        if (tcpServer.run("127.0.0.1", 9999)) {
            LOGGER.info("服务启动成功...");
        } else {
            LOGGER.info("服务启动失败");
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
