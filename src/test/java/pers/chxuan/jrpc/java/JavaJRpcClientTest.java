package pers.chxuan.jrpc.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.java.entity.RequestLogin;
import pers.chxuan.jrpc.java.serialize.JavaMessageSerialize;
import pers.chxuan.jrpc.rpc.JRpcClient;

public class JavaJRpcClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaJRpcClientTest.class);

    public static void main(String[] args) {
        JRpcClient rpcClient = new JRpcClient("127.0.0.1", 9999, new JavaMessageSerialize());

        RequestLogin message = new RequestLogin();
        message.setUsername("chxuan");
        message.setPassword("123456");

        RequestLogin resp = (RequestLogin) rpcClient.send(message);
        if (resp != null) {
            LOGGER.info("返回消息:{}", resp);
        } else {
            LOGGER.info("返回消息为空");
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
