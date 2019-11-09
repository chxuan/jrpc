package pers.chxuan.jrpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.net.TcpServer;

public class JRpcServerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JRpcServerTest.class);

    public static void main(String[] args) {
        LOGGER.info("JRpcServerTest");

        TcpServer tcpServer = new TcpServer(1);
        tcpServer.run("127.0.0.1", 9999);

        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
